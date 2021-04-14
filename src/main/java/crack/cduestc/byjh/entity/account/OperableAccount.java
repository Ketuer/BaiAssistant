package crack.cduestc.byjh.entity.account;

import crack.cduestc.byjh.entity.activity.SignedActivity;
import crack.cduestc.byjh.exception.ActivityOprException;

import java.util.List;

/**
 * 可操作账户接口，包含所有的常规操作
 *
 * @author Ketuer
 * @since 1.0
 */
public interface OperableAccount {
    void signActivity(int activityId) throws ActivityOprException;
    void cancelActivity(int activityId) throws ActivityOprException;
    List<SignedActivity> getActivities() throws ActivityOprException;
}
