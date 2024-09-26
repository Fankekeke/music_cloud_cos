package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.dao.*;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.service.*;
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

    private final IMusicPlayRecordService musicPlayRecordService;

    private final IBulletinInfoService bulletinInfoService;

    private final IAlbumInfoService albumInfoService;

    private final ISingerInfoService singerInfoService;

    private final IMusicInfoService musicInfoService;

    private final CollectInfoMapper collectInfoMapper;

    private final MessageInfoMapper messageInfoMapper;

    private final SubscriptionInfoMapper subscriptionInfoMapper;

    private final EvaluateInfoMapper evaluationMapper;

    private final AlbumInfoMapper albumInfoMapper;

    private final MusicInfoMapper musicInfoMapper;

    private final SingerInfoMapper singerInfoMapper;

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
     * 获取首页数据
     *
     * @return 结果
     */
    @GetMapping("/home")
    public R home() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("music", musicInfoMapper.queryHomeMusicList());
                put("singer", singerInfoMapper.queryHomeSingerList());
                put("album", albumInfoMapper.queryHomeAlbumList());
            }
        };
        return R.ok(result);
    }

    /**
     * 新增音乐评价信息
     *
     * @param evaluateInfo 音乐评价信息
     * @return 结果
     */
    @PostMapping("/addEvaluate")
    public R addEvaluate(@RequestBody EvaluateInfo evaluateInfo) {
        evaluateInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(evaluationMapper.insert(evaluateInfo));
    }

    /**
     * 根据用户获取订阅信息
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
     * 删除收藏
     *
     * @param collectId 收藏ID
     * @return 结果
     */
    @GetMapping("/delCollect")
    public R delCollect(@RequestParam("collectId") Integer collectId) {
        return R.ok(collectInfoMapper.deleteById(collectId));
    }

    /**
     * 删除关注
     *
     * @param subscriptionId 关注ID
     * @return 结果
     */
    @GetMapping("/delSubscription")
    public R delSubscription(@RequestParam("subscriptionId") Integer subscriptionId) {
        return R.ok(subscriptionInfoMapper.deleteById(subscriptionId));
    }

    /**
     * 删除评价
     *
     * @param evaluateId 评价ID
     * @return 结果
     */
    @GetMapping("/delEvaluate")
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
     * 获取用户收藏歌曲状态
     *
     * @param userId  用户ID
     * @param musicId 歌曲ID
     * @return 结果
     */
    @GetMapping("/queryCollectStatus")
    public R queryCollectStatus(@RequestParam("userId") Integer userId, @RequestParam("musicId") Integer musicId) {
        return R.ok(collectInfoMapper.selectCount(Wrappers.<CollectInfo>lambdaQuery().eq(CollectInfo::getUserId, userId).eq(CollectInfo::getMusicId, musicId)) > 0);
    }

    /**
     * 根据用户歌曲收藏
     *
     * @param userId  用户ID
     * @param musicId 歌曲ID
     * @return 结果
     */
    @GetMapping("/addCollectByMusic")
    public R addCollectByMusic(@RequestParam("userId") Integer userId, @RequestParam("musicId") Integer musicId) {
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setMusicId(musicId);
        collectInfo.setUserId(userId);
        collectInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(collectInfoMapper.insert(collectInfo));
    }

    /**
     * 根据用户ID和歌曲ID删除
     *
     * @param userId  用户ID
     * @param musicId 歌曲ID
     * @return 结果
     */
    @GetMapping("/delCollectByMusic")
    public R delCollectByMusic(@RequestParam("userId") Integer userId, @RequestParam("musicId") Integer musicId) {
        return R.ok(collectInfoMapper.delete(Wrappers.<CollectInfo>lambdaQuery().eq(CollectInfo::getUserId, userId).eq(CollectInfo::getMusicId, musicId)));
    }

    /**
     * 获取用户关注歌手状态
     *
     * @param userId   用户ID
     * @param singerId 歌手ID
     * @return 结果
     */
    @GetMapping("/querySubStatus")
    public R querySubStatus(@RequestParam("userId") Integer userId, @RequestParam("singerId") Integer singerId) {
        return R.ok(subscriptionInfoMapper.selectCount(Wrappers.<SubscriptionInfo>lambdaQuery().eq(SubscriptionInfo::getUserId, userId).eq(SubscriptionInfo::getSingerId, singerId)) > 0);
    }

    /**
     * 根据用户ID和歌手ID删除
     *
     * @param userId   用户ID
     * @param singerId 歌手ID
     * @return 结果
     */
    @GetMapping("/delSubByMusic")
    public R delSubByMusic(@RequestParam("userId") Integer userId, @RequestParam("singerId") Integer singerId) {
        return R.ok(subscriptionInfoMapper.delete(Wrappers.<SubscriptionInfo>lambdaQuery().eq(SubscriptionInfo::getUserId, userId).eq(SubscriptionInfo::getSingerId, singerId)));
    }

    /**
     * 根据专辑获取下的歌曲
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    @GetMapping("/queryMusicByAlbum")
    public R queryMusicByAlbum(@RequestParam("albumId") Integer albumId) {
        return R.ok(musicInfoMapper.selectMusicByAlbum(albumId));
    }

    /**
     * 获取歌手详细信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/querySingerDetail")
    public R querySingerDetail(@RequestParam("userId") Integer userId) {
        return R.ok(singerInfoService.querySingerDetail(userId));
    }

    /**
     * 获取歌曲详情
     *
     * @param musicId 歌曲ID
     * @return 结果
     */
    @GetMapping("/queryMusicDetail")
    public R queryMusicDetail(@RequestParam("musicId") Integer musicId, @RequestParam(value = "userId", required = false) Integer userId) {
        if (userId != null) {
            // 保存用户播放记录
            MusicPlayRecord musicPlayRecord = new MusicPlayRecord();
            musicPlayRecord.setUserId(userId);
            musicPlayRecord.setMusicId(musicId);
            musicPlayRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
            musicPlayRecordService.save(musicPlayRecord);
        }
        return R.ok(musicInfoService.queryMusicDetail(musicId));
    }

    /**
     * 获取专辑详情
     *
     * @param albumId 专辑ID
     * @return 结果
     */
    @GetMapping("/queryAlbumDetail")
    public R queryAlbumDetail(@RequestParam("albumId") Integer albumId) {
        return R.ok(albumInfoService.queryAlbumDetail(albumId));
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

    /**
     * 用户搜索
     *
     * @return 结果
     */
    @GetMapping("/selShopDetailList")
    public R selSearchDetailList(@RequestParam(value = "key", required = false) String key) {
        return R.ok(musicInfoService.selSearchDetailList(key));
    }

}
