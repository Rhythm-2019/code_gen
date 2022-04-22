package com.mdnote.rym;

import com.mdnote.rym.core.CoreGenerator;
import com.mdnote.rym.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.applet.AppletContext;

/**
 * @author Rhythm-2019
 * @date 2022/4/21
 * @description 启动类
 */
@Slf4j
public class App {

    public static void main(String[] args) {
        try {
            log.info("欢迎使用Code Generator代码生成器，如果在使用过程中遇到任何问题，欢迎在Gitee中提出issue或发送邮件到rhythm_2019@163.com");
            log.info("==================开始运行==================");

            CoreGenerator coreGenerator = CoreGenerator.newInstance();

            log.info("配置文件加载完成，马上进行代码生成");

            coreGenerator.execute();

            log.info("代码生成完毕，欢迎您再次使用！");
            log.info("帮我点个Start吧，谢谢！https://gitee.com/Rhythm-2019/code-gegerator");
        } catch (Exception e) {
            log.error("代码生成中发生异常，详细信息如下：");
            e.printStackTrace();
        }

    }
}
