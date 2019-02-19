package com.alibaba.fescar.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 22:54 2019/1/29
 * @ Description：${description}
 * @ Modified By：
 */
public class FescarNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        //support for annotation-driver
        registerBeanDefinitionParser("annotation-driver", new FescarBeanDefinitionParser());
        //support for registry center. todo
        //registerBeanDefinitionParser("registry", new FescarBeanDefinitionParser());
    }
}
