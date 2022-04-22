package com.mdnote.rym.engine;

import com.alibaba.fastjson.JSON;
import com.mdnote.rym.config.GlobalConfig;
import com.mdnote.rym.exception.GeneratorException;
import com.mdnote.rym.model.Table;
import com.mdnote.rym.util.FileUtil;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mdnote.rym.consts.GenerateConst.LOCAL_DIR;

/**
 * @author Rhythm-2019
 * @date 2022/4/21
 * @description
 */
@Slf4j
public class CommonEngine implements IEngine {

    private static final String TEMPLATE_LOCATION = LOCAL_DIR + "templates/%s/";

    private static final String CONFIG_NAME = "config.json";

    /**
     * 全局配置
     */
    protected GlobalConfig globalConfig;
    /**
     * 引擎配置信息
     */
    private SpringBootEngineConfig engineConfig;
    /**
     * freemarker 的核心类
     */
    private Configuration configuration;

    /**
     * 模板路径
     */
    private String templateLocation;

    public CommonEngine() {
    }

    @Override
    public void prepare(GlobalConfig globalConfig) {

        this.globalConfig = globalConfig;
        this.templateLocation = String.format(TEMPLATE_LOCATION, globalConfig.getSettings().getExpectProjectType());
        try {
            // 初始化渲染引擎
            this.configuration = new Configuration(Configuration.VERSION_2_3_28);
            // 初始化模板路径
            this.configuration.setTemplateLoader(new FileTemplateLoader(new File(this.templateLocation)));

            // 先渲染一下配置文件，再加载到内存
            StringWriter stringWriter = new StringWriter();
            this.configuration.getTemplate(CONFIG_NAME).process(this.globalConfig.getSettings(), stringWriter);
            this.engineConfig = JSON.parseObject(stringWriter.toString(), SpringBootEngineConfig.class);
        } catch (IOException e) {
            throw GeneratorException.readFileFailed(e);
        } catch (TemplateException e) {
            throw GeneratorException.generateFailed(e);
        }
    }

    @Override
    public void handle() {
        // 生成 Base、Common 等代码
        HashMap<String, Object> data = new HashMap<>(this.globalConfig.getSettings().fieldToMap());
        data.putAll(this.engineConfig.vars);
        // TODO 加入数据的方式能不能再重构一下
        data.put("datasource", this.globalConfig.getDatabase());

        for (SpringBootEngineConfigTemplateItem template : this.engineConfig.globalTemplates) {
            innerHandle(template.getOutput(), data, template.getName());
        }
    }

    @Override
    public void handle(Table table) {
        // 生成三层架构的代码
        HashMap<String, Object> data = new HashMap<>(this.globalConfig.getSettings().fieldToMap());
        data.putAll(this.engineConfig.vars);
        // TODO 加入数据的方式能不能再重构一下
        data.put("table", table);

        for (SpringBootEngineConfigTemplateItem templateItem : this.engineConfig.tableTemplates) {
            // 这里需要对输出路径进行一些替换
            SpringBootEngineConfigTemplateItem newItem = templateItem.copy();
            newItem.processTemplateParam(table);
            innerHandle(newItem.getOutput(), data, newItem.getName());
        }
    }

    @Override
    public void release() {
        // 关闭模板引擎
        this.configuration.clearTemplateCache();
    }

    private void innerHandle(String toFile, Map<String, Object> model, String templateFileName) {
        try (FileWriter fileWriter = new FileWriter(FileUtil.createFile(toFile))) {
            this.configuration.getTemplate(templateFileName).process(model, fileWriter);
        } catch (IOException | TemplateException e) {
            throw GeneratorException.generateFailed(e);
        }
    }

    @Data
    private static class SpringBootEngineConfig {
        private Map<String, Object> vars;
        private List<SpringBootEngineConfigTemplateItem> globalTemplates;
        private List<SpringBootEngineConfigTemplateItem> tableTemplates;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SpringBootEngineConfigTemplateItem {
        private String name;
        private String output;

        public SpringBootEngineConfigTemplateItem copy() {
            return new SpringBootEngineConfigTemplateItem(this.name, this.output);
        }
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        private void processTemplateParam(Table table) {
            // TODO 这里可能不太优雅，可以想一下怎么优化
            this.output = this.output.replace("${table.name}", table.getName());
        }
    }
}
