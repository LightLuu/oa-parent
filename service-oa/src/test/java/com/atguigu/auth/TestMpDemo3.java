package com.atguigu.auth;

import com.atguigu.model.process.Question;
import com.atguigu.process.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMpDemo3 {

    @Autowired
    public QuestionService questionService;

    @Test
    public void save(){
        Question question = new Question();
        question.setId(1L);
        question.setName("test");
        question.setDescription("测试save方法");
        question.setFormOptions("_drag_tag\":\"input\",\"hidden\":false,\"display\":true},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true},{\"type\":\"input\",\"field\":\"content\",\"title\":\"申请事由\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"switch\",\"field\":\"F9hn1n7twlkcfk\",\"title\":\"消息提示\",\"info\":\"\",\"_fc_drag_tag\":\"switch\",\"hidden\":false,\"display\":true},{\"type\":\"span\",\"title\":\"注意\",\"native\":false,\"children\":[\"有疑问找云尚小秘哦！\"],\"_fc_drag_tag\":\"span\",\"hidden\":false,\"display\":true}]");
        question.setFormProps("{\n" +
                "    \"form\": {\n" +
                "        \"labelPosition\": \"right\",\n" +
                "        \"size\": \"medium\",\n" +
                "        \"labelWidth\": \"80px\",\n" +
                "        \"hideRequiredAsterisk\": false,\n" +
                "        \"showMessage\": true,\n" +
                "        \"inlineMessage\": false\n" +
                "    },\n" +
                "    \"submitBtn\": {\n" +
                "                         \"innerText\":\"提交审批 \",\n" +
                "                        \"round\":true,\n" +
                "                        \"width\": \"280px\",\n" +
                "                        \"type\":\"primary\"\n" +
                "                     },\n" +
                "    \"resetBtn\": false\n" +
                "}\n");
        questionService.save(question);
    }
}
