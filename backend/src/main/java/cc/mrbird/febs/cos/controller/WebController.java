package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.dao.CollectInfoMapper;
import cc.mrbird.febs.cos.dao.EvaluateInfoMapper;
import cc.mrbird.febs.cos.dao.MessageInfoMapper;
import cc.mrbird.febs.cos.dao.SubscriptionInfoMapper;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.ICollectInfoService;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebController {

    private final IUserInfoService userInfoService;

    private final IBulletinInfoService bulletinInfoService;

    private final IMessageInfoService messageInfoService;

    private final CollectInfoMapper collectInfoMapper;

    private final MessageInfoMapper messageInfoMapper;

    private final SubscriptionInfoMapper subscriptionInfoMapper;

    private final EvaluateInfoMapper evaluationMapper;

    @PostMapping("/userAdd")
    public R userAdd(@RequestBody UserInfo user) throws Exception {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wx76a6577665633a86";//自己的appid
        url += "&secret=78070ccedf3f17b272b84bdeeca28a2e";//自己的appSecret
        url += "&js_code=" + user.getOpenId();
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(false).build();
        httpget.setConfig(requestConfig);
        response = httpClient.execute(httpget);
        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + res);
        }
        // 释放资源
        httpClient.close();
        response.close();
        String openid = JSON.parseObject(res).get("openid").toString();
        System.out.println("openid" + openid);
        int count = userInfoService.count(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getOpenId, openid));
        if (count > 0) {
            return R.ok(userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getOpenId, openid)));
        } else {
            user.setOpenId(openid);
            user.setCreateDate(DateUtil.formatDateTime(new Date()));
            user.setCode("UR-" + System.currentTimeMillis());
            user.setName(user.getUserName());
            user.setImages(user.getAvatar());
            userInfoService.save(user);
            return R.ok(user);
        }
    }

    @RequestMapping("/openid")
    public R getUserInfo(@RequestParam(name = "code") String code) throws Exception {
        System.out.println("code" + code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wx9fffb151ded22005";//自己的appid
        url += "&secret=9666e94c91361e7de4d3a6d09a23402f";//自己的appSecret
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);    //GET方式
        CloseableHttpResponse response = null;
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(false).build();
        httpget.setConfig(requestConfig);
        response = httpClient.execute(httpget);
        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + res);
        }
        // 释放资源
        httpClient.close();
        response.close();
        String openid = JSON.parseObject(res).get("openid").toString();
        System.out.println("openid" + openid);
        return R.ok(openid);
    }

    @GetMapping("/subscription")
    public R subscription(@RequestParam("taskCode") String taskCode) throws Exception {
        LinkedHashMap<String, Object> tokenParam = new LinkedHashMap<String, Object>() {
            {
                put("grant_type", "client_credential");
                put("appid", "wx76a6577665633a86");
                put("secret", "78070ccedf3f17b272b84bdeeca28a2e");
            }
        };
        String tokenResult = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", tokenParam);
        String token = JSON.parseObject(tokenResult).get("access_token").toString();
        LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>() {
            {
                put("thing1", new HashMap<String, Object>() {
                    {
                        put("value", "张三");
                    }
                });
                put("character_string3", new HashMap<String, Object>() {
                    {
                        put("value", taskCode);
                    }
                });
                put("time4", new HashMap<String, Object>() {
                    {
                        put("value", DateUtil.formatDateTime(new Date()));
                    }
                });
                put("thing5", new HashMap<String, Object>() {
                    {
                        put("value", "若查看详细内容，请登录小程序");
                    }
                });
            }
        };
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + token;
        LinkedHashMap<String, Object> subscription = new LinkedHashMap<String, Object>() {
            {
                put("touser", "oeDfR5zqxQD3EEA3uPT836qnauSc");
                put("template_id", "Z27pBK1n9WnKNtQ_fo7TC-nUJUlOQ9KVJk6LIgp0nH8");
                put("data", data);
            }
        };
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(subscription));
        return R.ok(result);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectUserInfo")
    public R selectUserInfo(@RequestParam("userId") Integer userId) {
        return R.ok(userInfoService.getById(userId));
    }

    /**
     * 根据用户获取
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/querySubscriptionSinger")
    public R querySubscriptionSinger(@RequestParam("userId") Integer userId) {
        return R.ok(subscriptionInfoMapper.selectSubByUser(userId));
    }

    /**
     * 根据用户获取评价信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/queryEvaluateByUser")
    public R queryEvaluateByUser(@RequestParam("userId") Integer userId) {
        return R.ok(evaluationMapper.queryEvaluateByUser(userId));
    }

    /**
     * 获取用户收藏歌曲
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/queryCollectByUser")
    public R queryCollectByUser(@RequestParam("userId") Integer userId) {
        return R.ok(collectInfoMapper.queryCollectByUserId(userId));
    }

    /**
     * 根据用户获取消息信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/queryMessageByUser")
    public R queryMessageByUser(@RequestParam("userId") Integer userId) {
        return R.ok(messageInfoMapper.selectList(Wrappers.<MessageInfo>lambdaQuery().eq(MessageInfo::getUserId, userId)));
    }

    /**
     * 删除消息
     *
     * @param evaluateId 评价ID
     * @return 结果
     */
    @GetMapping("/delMessage")
    public R delEvaluate(@RequestParam("evaluateId") Integer evaluateId) {
        return R.ok(evaluationMapper.deleteById(evaluateId));
    }

    /**
     * 删除消息
     *
     * @param messageId 消息ID
     * @return 结果
     */
    @GetMapping("/delMessage")
    public R delMessage(@RequestParam("messageId") Integer messageId) {
        return R.ok(messageInfoMapper.deleteById(messageId));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PostMapping("/editUserInfo")
    public R editUserInfo(@RequestBody UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 获取公告信息
     *
     * @return 结果
     */
    @GetMapping("/getBulletinList")
    public R getBulletinList() {
        return R.ok(bulletinInfoService.list());
    }

}
