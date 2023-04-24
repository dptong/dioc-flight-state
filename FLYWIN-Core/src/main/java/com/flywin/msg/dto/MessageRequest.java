package com.flywin.msg.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息请求
 *
 * @author xg-ran
 * @date 2022-01-11 09:35:02
 * @since 1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class MessageRequest {

    @ApiModelProperty(value = "消息回调id，依据此id可回调通知", required = true)
    private String callbackId;

    @ApiModelProperty(value = "消息定义编码", required = true)
    private String messageDefineCode;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "采购单号")
    private String purchaseOrderNo;

    @ApiModelProperty(value = "件号")
    private String partNo;

    @ApiModelProperty(value = "系统内消息")
    private SysMsg sysMsg;

    @ApiModelProperty(value = "短信")
    private SmsMsg smsMsg;

    @ApiModelProperty(value = "邮件")
    private EmailMsg emailMsg;

    @ApiModelProperty(value = "手机推送")
    private PushMsg pushMsg;

    @ApiModelProperty(value = "收信人【消息定义中已配置收信人，此处不需要指定；反之，需要指定收信人】")
    private List<Recipient> recipients = new ArrayList<>();

    /**
     * 添加收信人
     *
     * @param o o
     * @return MessageRequest
     */
    public MessageRequest addRecipient(Recipient o) {
        this.recipients.add(o);
        return this;
    }

    /**
     * 序列化成Json字符串
     *
     * @return String
     */
    public String serialize() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.writeValueAsString(this);
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    public static class SysMsg {

        @ApiModelProperty(value = "标题", required = true)
        private String title;

        @ApiModelProperty(value = "正文")
        private String content;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class SmsMsg {
        @ApiModelProperty(value = "短信内容", required = true)
        private String content;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class EmailMsg {
        @ApiModelProperty(value = "标题", required = true)
        private String title;

        @ApiModelProperty(value = "正文")
        private String content;

        @ApiModelProperty(value = "附件Id，多个附件之间用','隔开")
        private String annexIds;

        @ApiModelProperty(value = "抄送人邮箱地址，多个地址之间用','隔开")
        private String ccAddress;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class PushMsg {
        @ApiModelProperty(value = "标题", required = true)
        private String title;

        @ApiModelProperty(value = "正文")
        private String content;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Recipient {

        @ApiModelProperty(value = "收信者类型编码", required = true)
        private String typeCode = RecipientType.USER_ID.getCode();

        @ApiModelProperty(value = "收信者【用户id / 机构id / 运代公司联系人id / 角色编码 /"
                + " 手机号码（短信有效）/ 邮箱地址（邮件有效，支持群发邮件，以','隔开）】", required = true)
        private String key;

    }

}
