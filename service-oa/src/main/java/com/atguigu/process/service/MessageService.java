package com.atguigu.process.service;

public interface MessageService {

    /**
     * 推送待审批人员
     * @param processId
     * @param userId
     * @param taskId
     */
    void pushPendingMessage(Long processId, Long userId, String taskId);

    /**
     * 推送审批已经完成
     * @param processId
     * @param userId
     * @param status
     */
    public void pushProcessedMessage(Long processId, Long userId, Integer status);
}
