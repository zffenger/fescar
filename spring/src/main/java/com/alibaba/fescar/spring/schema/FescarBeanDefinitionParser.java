package com.alibaba.fescar.spring.schema;

import com.alibaba.fescar.common.exception.FrameworkException;
import com.alibaba.fescar.common.util.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 22:55 2019/1/29
 * @ Description：${description}
 * @ Modified By：
 */
public class FescarBeanDefinitionParser implements BeanDefinitionParser {

    private static final String GLOBAL_TRANSACTION_SCANNER = "com.alibaba.fescar.spring.annotation.GlobalTransactionScanner";
    //the default transaction service group

    private static final String APPLICATION_ID = "applicationId";

    private static final String TRANSACTION_SERVICE_GROUP = "transactionServiceGroup";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        //the scanner will be registered only once
        if (!parserContext.getRegistry().containsBeanDefinition(GLOBAL_TRANSACTION_SCANNER)) {
            RootBeanDefinition def = new RootBeanDefinition();
            def.setBeanClassName(GLOBAL_TRANSACTION_SCANNER);
            // Mark as infrastructure bean and attach source location.
            def.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            def.setSource(parserContext.extractSource(element));
            //user config
            String applicationId = element.getAttribute(APPLICATION_ID);
            String txServiceGroup = element.getAttribute(TRANSACTION_SERVICE_GROUP);
            notEmpty(applicationId,"applicationId attribute is empty");
            notEmpty(txServiceGroup,"transactionServiceGroup attribute is empty");
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0,applicationId);
            constructorArgumentValues.addIndexedArgumentValue(1,txServiceGroup);
            def.setConstructorArgumentValues(constructorArgumentValues);
            //register
            parserContext.getRegistry().registerBeanDefinition(GLOBAL_TRANSACTION_SCANNER, def);
        }
        return null;
    }

    private void notEmpty(String attribute, String comment)
    {
        if (StringUtils.isEmpty(attribute)){
            throw new FrameworkException(comment);
        }
    }
}
