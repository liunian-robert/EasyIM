package com.easyim.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easyim.common.config.FastDFSConfig;
import com.easyim.common.constant.Constants;
import com.easyim.common.core.controller.BaseController;
import com.easyim.common.core.domain.AjaxResult;
import com.easyim.common.core.page.PageDataInfo;
import com.easyim.common.fastdfs.client.FastDFSClient;
import com.easyim.common.utils.EmptyUtils;
import com.easyim.common.utils.StringUtils;
import com.easyim.framework.util.ShiroUtils;
import com.easyim.system.domain.*;
import com.easyim.system.service.*;
import com.easyim.web.controller.system.message.response.SysFriendTypeResponse;
import com.easyim.web.controller.system.message.response.SysGroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class SysMessageController extends BaseController {
    @Autowired
    private ISysGroupService sysGroupService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysFriendTypeService sysFriendTypeService;
    @Autowired
    private ISysFriendUserService sysFriendUserService;
    @Autowired
    private FastDFSConfig fastDFSConfig;
    @Autowired
    private ISysGroupMemberService sysGroupMemberService;
    @Autowired
    private ISysNotifyService sysNotifyService;
    @Autowired
    private ISysMessageService sysMessageService;
    @Autowired
    private ISysChatService sysChatService;

    @GetMapping("/message/init")
    @ResponseBody
    public AjaxResult getPersonalInformation(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = ShiroUtils.getSysUser();
        SysMessageUser mine = new SysMessageUser();
        Map<String, Object> result = new HashMap<String,Object>();
        if (sysUser != null) {
            mine.setUsername(sysUser.getUserName());
            mine.setId(sysUser.getUserId());
            mine.setStatus("online");
            mine.setSign(sysUser.getSign());
            mine.setAvatar(sysUser.getAvatarUrl());
            result.put("mine",mine);
        }
        List<SysGroup> sysGroups = sysGroupService.selectSysGroupByUserId(sysUser.getUserId());
        List<SysGroupResponse> groupResponses = new ArrayList<SysGroupResponse>();
        if (EmptyUtils.isNotEmpty(sysGroups)) {
            for (SysGroup group : sysGroups) {
                SysGroupResponse sysGroupResponse = new SysGroupResponse();
                sysGroupResponse.setId(group.getId());
                sysGroupResponse.setGroupname(group.getGroupname());
                if (EmptyUtils.isNotEmpty(group.getAvatar())) {
                    String avatarUrl = fastDFSConfig.getDownload_url() + group.getAvatar();
                    sysGroupResponse.setAvatar(avatarUrl);
                }
                groupResponses.add(sysGroupResponse);
            }
            result.put("group",groupResponses);
        }
        List<SysFriendType> sysFriendTypes = sysFriendTypeService.selectSysFriendTypeByUserId(sysUser.getUserId());
        List<SysFriendTypeResponse> friendTypeResponses = new ArrayList<SysFriendTypeResponse>();
        if (EmptyUtils.isNotEmpty(sysFriendTypes)) {
            for (SysFriendType friendType : sysFriendTypes) {
                SysFriendTypeResponse friendTypeResponse = new SysFriendTypeResponse();
                friendTypeResponse.setId(friendType.getId());
                friendTypeResponse.setGroupname(friendType.getGroupname());
                List<SysFriendUser> sysFriendUsers = sysFriendUserService.selectSysFriendUserByTypeId(friendType.getId());
                if (EmptyUtils.isNotEmpty(sysFriendUsers)) {
                    for (SysFriendUser friendUser : sysFriendUsers) {
                        if (EmptyUtils.isNotEmpty(friendUser.getAvatar())) {
                            String avatarUrl = fastDFSConfig.getDownload_url() + friendUser.getAvatar();
                            friendUser.setAvatar(avatarUrl);
                            friendUser.setStatus(friendUser.getStatus().equals("1")?"online":"offline");
                        }
                    }
                }
                friendTypeResponse.setList(sysFriendUsers);
                friendTypeResponses.add(friendTypeResponse);
            }
            result.put("friend",friendTypeResponses);
        }
        return AjaxResult.success("success",result);
    }


    @GetMapping("/message/group/members")
    @ResponseBody
    public AjaxResult getGroupMembers(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        Map<String,Object> result = new HashMap<String,Object>();
        if (EmptyUtils.isEmpty(id)) {
            result.put("list",null);
            return AjaxResult.success("success",result);
        }
        List<SysGroupMember> sysGroupMembers = sysGroupMemberService.findSysGroupMember(id);
        List<SysMessageUser> sysMessageUsers = new ArrayList<SysMessageUser>();
        if (EmptyUtils.isNotEmpty(sysGroupMembers)) {
            for (SysGroupMember friendUser : sysGroupMembers) {
                SysMessageUser sysMessageUser = new SysMessageUser();
                SysUser sysUser = sysUserService.selectUserById(friendUser.getUserid());
                sysMessageUser.setId(friendUser.getUserid());
                sysMessageUser.setSign(sysUser.getSign());
                sysMessageUser.setStatus(sysUser==null || sysUser.getState()==0?"offline":"online");
                sysMessageUser.setUsername(friendUser.getRemark());
                if (EmptyUtils.isNotEmpty(sysUser.getAvatar())) {
                    String avatarUrl = fastDFSConfig.getDownload_url() + sysUser.getAvatar();
                    sysMessageUser.setAvatar(avatarUrl);
                }
                sysMessageUsers.add(sysMessageUser);
            }
        }
        result.put("list",sysMessageUsers);
        return AjaxResult.success("success",result);
    }

    @GetMapping("/message/friend/lookup")
    @ResponseBody
    public AjaxResult findFriends(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("content") String content,
                                  @RequestParam("type") String type) {
        Map<String,Object> result = new HashMap<String,Object>();
        String userId = ShiroUtils.getSysUser().getUserId();
        if (EmptyUtils.isNotEmpty(type) && type.equals("friend")) {//查找好友
            startPage();
            List<SysFriendUser> user = sysFriendUserService.findSysFriend(content,userId);
            if (EmptyUtils.isNotEmpty(user)) {
                for (SysFriendUser sysFriendUser : user) {
                    if (EmptyUtils.isNotEmpty(sysFriendUser.getAvatar())) {
                        String avatarUrl = fastDFSConfig.getDownload_url() + sysFriendUser.getAvatar();
                        sysFriendUser.setAvatar(avatarUrl);
                    }
                }
            }
            result.put("user",getPageDataTable(user));
        }
        if (EmptyUtils.isNotEmpty(type) && type.equals("group")) {//查找群组
            startPage();
            List<SysGroup> user = sysGroupService.findSysGroup(content,userId);
            if (EmptyUtils.isNotEmpty(user)) {
                for (SysGroup sysGroup : user) {
                    if (EmptyUtils.isNotEmpty(sysGroup.getAvatar())) {
                        String avatarUrl = fastDFSConfig.getDownload_url() + sysGroup.getAvatar();
                        sysGroup.setAvatar(avatarUrl);
                    }
                }
            }
            result.put("user",getPageDataTable(user));
        }
        return AjaxResult.success("success",result);
    }

    @GetMapping("/message/group/lookGroupRemark")
    @ResponseBody
    public AjaxResult lookGroupRemark(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("groupid") String groupid) {
        SysGroupMember sysGroupMember = sysGroupMemberService.getSysGroupMemberByUserId(groupid,ShiroUtils.getUserId());
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("groupmember",sysGroupMember);
        return AjaxResult.success("success",result);
    }


    @GetMapping("/message/friendtype/add")
    @ResponseBody
    public AjaxResult addFriendType(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("groupname") String groupname) {
        SysFriendType sysFriendType = new SysFriendType();
        sysFriendType.setId(StringUtils.getUUID());
        sysFriendType.setGroupname(groupname);
        sysFriendType.setUserid(ShiroUtils.getUserId());
        int sort = sysFriendTypeService.selectSort(ShiroUtils.getUserId());
        sysFriendType.setSort(sort);
        int reuslt = sysFriendTypeService.saveSysFriendType(sysFriendType);
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("group",sysFriendType);
        return AjaxResult.success("success",data);
    }


    @GetMapping("/message/friendtype/update")
    @ResponseBody
    public AjaxResult updateFriendType(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("targetIndex") Integer targetIndex,
                                    @RequestParam("groupname") String groupname) {
        if (EmptyUtils.isEmpty(groupname)) {
            groupname = "默认分组";
        }
        List<SysFriendType> sysFriendTypes = sysFriendTypeService.selectSysFriendTypeByUserId(ShiroUtils.getUserId());
        if (EmptyUtils.isNotEmpty(sysFriendTypes)) {
            for (int i =0;i<sysFriendTypes.size();i++) {
                if(i==targetIndex) {
                    sysFriendTypeService.updateSysFriendType(sysFriendTypes.get(i).getId(),groupname);
                }
            }
        }
        return AjaxResult.success("success");
    }

    @GetMapping("/message/friendtype/del")
    @ResponseBody
    public AjaxResult delFriendType(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("targetIndex") Integer targetIndex) {
        List<SysFriendType> sysFriendTypes = sysFriendTypeService.selectSysFriendTypeByUserId(ShiroUtils.getUserId());
        SysFriendType todel = null;
        if (EmptyUtils.isNotEmpty(sysFriendTypes)) {
            for (int i =0;i<sysFriendTypes.size();i++) {
                if(i==targetIndex) {
                    todel = sysFriendTypes.get(i);
                }
            }
        }
        if (todel != null) {
            if (sysFriendTypes.size() == 1) {
                return AjaxResult.error("至少保留一个分组");
            }
            List<SysFriendUser> friends = sysFriendUserService.selectSysFriendUserByTypeId(todel.getId());
            if(friends.size()>0) {
                return AjaxResult.error("该分组下还有好友");
            }
            sysFriendTypeService.delSysFriendType(todel.getId());
        }
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("group",todel);
        return AjaxResult.success("success",todel);
    }


    /**
     * 获取聊天记录
     */
    @GetMapping("/message/chat/record")
    @ResponseBody
    public AjaxResult chatRecord(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("type") String type,
                           @RequestParam("id") String id) {
        SysUser sysUser = ShiroUtils.getSysUser();
        //好友聊天记录
        if("friend".equals(type)) {
            startPage();
            List<SysChatRecord> records = sysChatService.findChatRecord(id,sysUser.getUserId());
            if (EmptyUtils.isNotEmpty(records)) {
                for (SysChatRecord sysChatRecord : records) {
                    if (EmptyUtils.isNotEmpty(sysChatRecord.getAvatar())) {
                        sysChatRecord.setAvatar(fastDFSConfig.getDownload_url() + sysChatRecord.getAvatar());
                    }
                }
            }
            PageDataInfo pageInfo = getPageDataTable(records);
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("records",pageInfo);
            return AjaxResult.success("success",result);
        } else {//群聊天记录
            startPage();
            List<SysChatRecord> records = sysChatService.findGroupChatRecord(id);
            if (EmptyUtils.isNotEmpty(records)) {
                for (SysChatRecord sysChatRecord : records) {
                    if (EmptyUtils.isNotEmpty(sysChatRecord.getAvatar())) {
                        sysChatRecord.setAvatar(fastDFSConfig.getDownload_url() + sysChatRecord.getAvatar());
                    }
                }
            }
            PageDataInfo pageInfo = getPageDataTable(records);
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("records",pageInfo);
            return AjaxResult.success("success",result);
        }

    }

    @GetMapping("/message/group/remark")
    @ResponseBody
    public AjaxResult lookGroupRemark(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("groupid") String groupid,
                                      @RequestParam("remark") String remark) {
        int result = sysGroupMemberService.modifyGroupRemark(groupid,ShiroUtils.getUserId(),remark);
        return AjaxResult.success("success",result);
    }

    @GetMapping("/message/group/join")
    @ResponseBody
    public AjaxResult joinGroupChat(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("id") String id) throws Exception {
        String userid = ShiroUtils.getUserId();
        List<SysGroupMember> sysGroupMembers = sysGroupMemberService.findSysGroupMember(id);
        if (EmptyUtils.isNotEmpty(sysGroupMembers)) {
            for (SysGroupMember sysGroupMember : sysGroupMembers) {
                if (!sysGroupMember.getUserid().equals(userid)) {
                    Map<String,Object> result = new HashMap<String,Object>();
                    Map<String,Object> msg = new HashMap<String,Object>();
                    msg.put("system",true);
                    msg.put("id",id);
                    msg.put("type","group");
                    msg.put("content",ShiroUtils.getSysUser().getUserName()+"加入群聊");
                    result.put("type",12);
                    result.put("msg",msg);
                    AjaxResult ajaxResult = AjaxResult.success("success",result);
                    sysMessageService.sendMessage(sysGroupMember.getUserid(),JSON.toJSONString(ajaxResult));
                }
            }
        }
        return AjaxResult.success("success");
    }

    @GetMapping("/message/send")
    @ResponseBody
    public AjaxResult sendMessage(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("type") String type,
                                      @RequestParam("message") String message) throws Exception {
        JSONObject messageJson=JSONObject.parseObject(message);
        JSONObject mine=messageJson.getJSONObject("mine");
        JSONObject to=messageJson.getJSONObject("to");
        JSONObject msg=new JSONObject();
        JSONObject result=new JSONObject();

        SysChat chat=new SysChat();
        chat.setId(mine.getString("cid"));
        chat.setFromid(mine.getString("id"));
        chat.setContent(mine.getString("content"));
        chat.setType(to.getString("type"));
        chat.setTo_userid(to.getString("id"));
        chat.setTimestamp(new Date().getTime());

        msg.put("username", mine.getString("username"));
        msg.put("cid",chat.getId());
        msg.put("avatar", mine.getString("avatar"));
        msg.put("type", to.getString("type"));
        msg.put("content", mine.getString("content"));
        msg.put("fromid", mine.getString("id"));
        msg.put("toid",to.getString("id"));
        msg.put("timestamp", System.currentTimeMillis());
        //好友发送消息
        if(to.getString("type").equals("friend")) {
            Map<String,Object> inputMsg = new HashMap<String,Object>();
            inputMsg.put("type",11);
            SysMessageUser sysMessageUser = new SysMessageUser();
            sysMessageUser.setId(mine.getString("id"));
            inputMsg.put("user",sysMessageUser);
            AjaxResult ajaxResult = AjaxResult.success("success",inputMsg);
            sysMessageService.sendMessage(to.getString("id"),JSON.toJSONString(ajaxResult));
            Thread.sleep(500);
            SysUser sysUser=sysUserService.selectUserById(to.getString("id"));
            //判断接收着是否在线
            if(sysUser.getState() !=1) {//离线
                chat.setStatus(2);
                //发送信息保存到数据库
                sysChatService.saveSysChat(chat);
            }else {//在线
                msg.put("id", mine.getString("id"));
                chat.setStatus(1);
                //保存到数据库
                sysChatService.saveSysChat(chat);
                //设置消息类型
                result.put("type",1);
                result.put("data", msg);
                sysMessageService.sendMessage(to.getString("id"),result.toJSONString());
            }
        }else { //群组消息
            List<SysGroupMember> sysGroupMembers = sysGroupMemberService.findSysGroupMember(to.getString("id"));
            //循环发送群消息，你也可以采用群topic发布
            for (SysGroupMember groupMember : sysGroupMembers) {
                //不用在给自己发一遍
                if(!groupMember.getUserid().equals(mine.getString("id"))) {
                    msg.put("id", to.getString("id"));
                    SysUser sysUser = sysUserService.selectUserById(groupMember.getUserid());
                    if (sysUser.getState() !=1) {//离线
                        chat.setStatus(2);
                        chat.setTo_userid(groupMember.getUserid());
                        chat.setTo_groupid(to.getString("id"));
                        //保存到数据库
                        sysChatService.saveSysChat(chat);
                    } else {//在线
                        chat.setStatus(1);
                        chat.setStatus(2);
                        chat.setTo_userid(groupMember.getUserid());
                        chat.setTo_groupid(to.getString("id"));
                        //保存到数据库
                        sysChatService.saveSysChat(chat);
                        msg.put("mine", false);// 是否为我发送
                        result.put("type", 1);
                        result.put("data", msg);
                        sysMessageService.sendMessage(groupMember.getUserid(),result.toJSONString());
                    }
                }
            }
        }
        return AjaxResult.success("success");
    }

    @GetMapping("/message/chat/del")
    @ResponseBody
    public AjaxResult delChatMessage(HttpServletRequest request, HttpServletResponse response,@RequestParam("cid") String cid) throws Exception {

        //好友发送消息
        SysChat sysChat = sysChatService.getSysChatById(cid);
        if (sysChat != null) {
            sysChatService.delSysChatById(cid);
        }
        return AjaxResult.success("success",sysChat);
    }


    @GetMapping("/message/chat/cancel")
    @ResponseBody
    public AjaxResult cancelChatMessage(HttpServletRequest request, HttpServletResponse response,@RequestParam("cid") String cid) throws Exception {

        //好友发送消息
        SysChat sysChat = sysChatService.getSysChatById(cid);
        if (sysChat != null) {
            if (sysChat.getType().equals("friend")) {
                Map<String,Object> result = new HashMap<String,Object>();
                result.put("type",13);
                result.put("cid",cid);
                result.put("id",ShiroUtils.getUserId());
                result.put("data",sysChat);
                sysMessageService.sendMessage(sysChat.getTo_userid(),JSON.toJSONString(AjaxResult.success("success",result)));
            }
            if (sysChat.getType().equals("group")) {
                List<SysGroupMember> members = sysGroupMemberService.findSysGroupMember(sysChat.getTo_groupid());
                if (EmptyUtils.isNotEmpty(members)) {
                    for (SysGroupMember sysGroupMember : members) {
                        if (!sysGroupMember.getUserid().equals(ShiroUtils.getUserId())) {
                            Map<String,Object> result = new HashMap<String,Object>();
                            result.put("type",13);
                            result.put("cid",cid);
                            result.put("id",sysGroupMember.getUserid());
                            result.put("data",sysChat);
                            sysMessageService.sendMessage(sysChat.getTo_userid(),JSON.toJSONString(AjaxResult.success("success",result)));
                        }
                    }
                }
            }
        }
        return AjaxResult.success("success",sysChat);
    }


    @GetMapping("/message/user/info")
    @ResponseBody
    public AjaxResult getUserInfo(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("id") String id) {
        SysUser sysUser = sysUserService.selectUserById(id);
        SysMessageUser messageUser = new SysMessageUser();
        messageUser.setId(sysUser.getUserId());
        messageUser.setUsername(sysUser.getUserName());
        messageUser.setStatus(sysUser.getState()==null||sysUser.getState()==0?"offline":"online");
        messageUser.setSign(sysUser.getSign());
        if (EmptyUtils.isNotEmpty(sysUser.getAvatar())) {
            String avatarUrl = fastDFSConfig.getDownload_url() + sysUser.getAvatar();
            messageUser.setAvatar(avatarUrl);
        }
        Map<String,Object> user = new HashMap<String,Object>();
        user.put("user",messageUser);
        return AjaxResult.success("success",user);
    }

    /**
     * 退群
     */
    @GetMapping("/message/group/exit")
    @ResponseBody
    public AjaxResult exitGroup(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam("groupid") String groupid) {
        AjaxResult ajaxResult = null;
        SysUser sysUser = ShiroUtils.getSysUser();
        SysGroupMember sysGroupMember = sysGroupMemberService.getSysGroupMemberByUserId(groupid,sysUser.getUserId());
        if(sysGroupMember.getType() != 1) {
            int result = sysGroupMemberService.deleteGroupMemberById(sysGroupMember.getId());
            ajaxResult = AjaxResult.success("success",result);
        }
        if (sysGroupMember.getType() == 1) {
            List<SysGroupMember> groupMembers = sysGroupMemberService.findSysGroupMember(groupid);
            if (groupMembers.size() == 1) {
                int result = sysGroupMemberService.deleteGroupMemberById(groupMembers.get(0).getId());
                result = sysGroupService.deleteGroupById(groupMembers.get(0).getGroupid());
                ajaxResult = AjaxResult.success("success",result);
            } else {
                ajaxResult = AjaxResult.error("群主只可以最后一个退群");
            }
        }
        return ajaxResult;
    }

    @GetMapping("/message/friend/group")
    @ResponseBody
    public AjaxResult createFriendType(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("groupname") String groupname,
                                       @RequestParam("avatar") String avatar,
                                       @RequestParam("des")String des) {
        if (EmptyUtils.isEmpty(avatar)) {
            avatar="";
        }
        SysGroup sysGroup = new SysGroup();
        sysGroup.setId(StringUtils.getUUID());
        sysGroup.setGroupname(groupname);
        sysGroup.setBelong(ShiroUtils.getUserId());
        sysGroup.setAvatar(avatar);
        sysGroup.setDes(des);
        sysGroup.setStatus(""+0);
        sysGroup.setNumber(500);
        sysGroup.setTimestamp(System.currentTimeMillis());
        int result = sysGroupService.saveSysGroup(sysGroup);
        SysGroupMember sysGroupMember = new SysGroupMember();
        sysGroupMember.setStatus(0);
        sysGroupMember.setUserid(ShiroUtils.getUserId());
        sysGroupMember.setType(1);
        sysGroupMember.setGroupid(sysGroup.getId());
        sysGroupMember.setRemark(ShiroUtils.getSysUser().getUserName());
        sysGroupMember.setTimestamp(System.currentTimeMillis());
        sysGroupMember.setId(StringUtils.getUUID());
        result = sysGroupMemberService.saveSysGroupMember(sysGroupMember);
        Map<String,Object> res = new HashMap<String,Object>();
        if (EmptyUtils.isNotEmpty(sysGroup.getAvatar())) {
            String avatarUrl = fastDFSConfig.getDownload_url() + sysGroup.getAvatar();
            sysGroup.setAvatar(avatarUrl);
        }
        res.put("group",sysGroup);
        return AjaxResult.success("success",res);
    }

    @GetMapping("/message/friend/del")
    @ResponseBody
    public AjaxResult delFriend(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("uid") String uid) throws Exception {
        String userid = ShiroUtils.getUserId();
        int result = sysFriendUserService.delSysFriend(userid,uid);
        sysFriendUserService.delSysFriend(uid,userid);
        return AjaxResult.success("success",result);
    }

    @GetMapping("/message/friend/remark")
    @ResponseBody
    public AjaxResult delFriend(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("uid") String uid,
                                @RequestParam(value = "remark",required = false) String remark) throws Exception {
        String userid = ShiroUtils.getUserId();
        int result = sysFriendUserService.updateFriendRemark(userid,uid,remark);
        return AjaxResult.success("success",result);
    }

    @GetMapping("/message/friend/add")
    @ResponseBody
    public AjaxResult addFriendOrGroup(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("type") String type,
                                       @RequestParam(value = "groupid",required = false) String groupid,
                                       @RequestParam("remark")String remark,
                                       @RequestParam("toid") String toid) throws Exception {
        SysUser user = ShiroUtils.getSysUser();
        SysNotify notify = new SysNotify();
        notify.setId(StringUtils.getUUID());
        notify.setStatus(1);//设置为未读
        notify.setRemark(remark);
        notify.setTimestamp(System.currentTimeMillis()+"");
        notify.setFrom(user.getUserId());
        if("friend".equals(type)) {
            notify.setTo(toid);
            notify.setHandlerid(toid);
            notify.setType(1);
            notify.setGroupid(groupid);
        } else {
            SysGroup sysGroup = sysGroupService.getSysGroupById(toid);
            notify.setTo(sysGroup.getBelong());
            notify.setHandlerid(sysGroup.getBelong());
            notify.setType(3);
            notify.setGroupid(sysGroup.getId());
        }
        if("group".equals(type)) {
            List<SysGroupMember> members = sysGroupMemberService.findSysGroupMember(toid);
            SysGroup sysGroup = sysGroupService.getSysGroupById(toid);
            if(members.size() >= sysGroup.getNumber()) {
                return AjaxResult.error("群成员已达上限");
            }else {
                List<SysNotify> notifies = sysNotifyService.findSysNotify(notify.getFrom(),notify.getTo(),notify.getGroupid(),notify.getType());
                if(notifies.size() >0) {
                    return AjaxResult.error("不能重复申请");
                }else {
                    sysNotifyService.saveSysNotify(notify);
                    SysUser sysUser = sysUserService.selectUserById(notify.getHandlerid());
                    if(sysUser.getState() ==1) {
                        //发送消息
                        Map<String,Object> result= new HashMap<String,Object>();
                        List<SysNotify> sysNotifies = sysNotifyService.findSysNotifyByHandlerId(sysUser.getUserId());
                        result.put("msgbox",sysNotifies.size());
                        result.put("type",7);
                        AjaxResult ajaxResult = AjaxResult.success("success",result);
                        sysMessageService.sendMessage(sysUser.getUserId(), JSON.toJSONString(ajaxResult));
                    }
                    return AjaxResult.success("申请发送成功");
                }
            }
        } else {
            sysNotifyService.saveSysNotify(notify);
            SysUser sysUser = sysUserService.selectUserById(notify.getHandlerid());
            if(sysUser.getState() ==1) {
                Map<String,Object> result= new HashMap<String,Object>();
                List<SysNotify> sysNotifies = sysNotifyService.findSysNotifyByHandlerId(sysUser.getUserId());
                result.put("msgbox",sysNotifies.size());
                result.put("type",7);
                AjaxResult ajaxResult = AjaxResult.success("success",result);
                sysMessageService.sendMessage(sysUser.getUserId(), JSON.toJSONString(ajaxResult));
            }
            return AjaxResult.success("申请发送成功");
        }
    }


    @GetMapping("/message/box")
    @ResponseBody
    public AjaxResult getMessageBox(HttpServletRequest request, HttpServletResponse response) {
        String userid = ShiroUtils.getUserId();
        List<SysNotify> sysNotifys = sysNotifyService.findSysNotifyByHandlerId(userid);
        List<Map<String,Object>> result = new ArrayList<>();
        for (SysNotify notify : sysNotifys) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("from",notify.getFrom());
            map.put("id",notify.getId());
            map.put("groupid",notify.getGroupid());
            map.put("handlerid",notify.getHandlerid());
            map.put("remark",notify.getRemark());
            map.put("status",notify.getStatus());
            map.put("timestamp",notify.getTimestamp());
            map.put("to",notify.getTo());
            map.put("type",notify.getType());
            if(notify.getType()==3) {
                SysGroup sysGroup = sysGroupService.getSysGroupById(notify.getGroupid());
                map.put("groupname", sysGroup.getGroupname());
            }
            SysUser sysUser = sysUserService.selectUserById(notify.getFrom());
            SysMessageUser user = new SysMessageUser();
            user.setId(sysUser.getUserId());
            user.setUsername(sysUser.getUserName());
            user.setSign(sysUser.getSign());
            user.setStatus(sysUser.getState()==null || sysUser.getState()==1?"online":"offline");
            if (EmptyUtils.isNotEmpty(sysUser.getAvatar())) {
                String avatarUrl = fastDFSConfig.getDownload_url() + sysUser.getAvatar();
                user.setAvatar(avatarUrl);
            }
            map.put("user",user);
            result.add(map);
        }
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("notifies",result);
        return AjaxResult.success("success",res);
    }

    /**
     * 获取离线消息，暂时体验版未开放群离线消息
     */
    @GetMapping("/message/offline")
    @ResponseBody
    public AjaxResult getOfflineMessage(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = ShiroUtils.getSysUser();

        //获取离线消息
        List<SysChat> chats=sysChatService.findSysChatByUserId(sysUser.getUserId());
        List<Map<String,Object>> offlineMessage = new ArrayList<Map<String,Object>>();
        if (EmptyUtils.isNotEmpty(chats)) {
            for (SysChat sysChat : chats) {
                Map<String,Object> map = new HashMap<String,Object>();
                if (sysChat.getType().equals("friend")) {
                    SysUser sysUserx = sysUserService.selectUserById(sysChat.getFromid());
                    map.put("id",sysUserx.getUserId());
                    map.put("username",sysUserx.getUserName());
                    map.put("content",sysChat.getContent());
                    map.put("type",sysChat.getType());
                    map.put("fromid",sysChat.getFromid());
                    if (EmptyUtils.isNotEmpty(sysUserx.getAvatar())) {
                        String avatarUrl = fastDFSConfig.getDownload_url() + sysUserx.getAvatar();
                        map.put("avatar",avatarUrl);
                    }
                }
                if (sysChat.getType().equals("group")) {
                    String groupid = sysChat.getTo_groupid();
                    SysGroup sysGroup = sysGroupService.getSysGroupById(groupid);
                    SysUser sysUserx = sysUserService.selectUserById(sysChat.getFromid());
                    map.put("id",sysGroup.getId());
                    map.put("username",sysUserx.getUserName());
                    map.put("content",sysChat.getContent());
                    map.put("type",sysChat.getType());
                    map.put("fromid",sysChat.getFromid());
                    if (EmptyUtils.isNotEmpty(sysUserx.getAvatar())) {
                        String avatarUrl = fastDFSConfig.getDownload_url() + sysUserx.getAvatar();
                        map.put("avatar",avatarUrl);
                    }
                }
                offlineMessage.add(map);
            }
        }
        //离线消息修改为已读
        sysChatService.updateSysChatStatus(sysUser.getUserId());
        List<SysNotify> sysNotifies = sysNotifyService.findSysNotifyByHandlerId(sysUser.getUserId());
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("type",1);
        result.put("data",offlineMessage);
        result.put("msgbox",sysNotifies.size());
        return AjaxResult.success("success", result);
    }

    /**
     * 根据id获取群组信息
     */
    @GetMapping("/message/group/info")
    @ResponseBody
    public AjaxResult getGroupInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam("id")String id) {
        SysGroup sysGroup = sysGroupService.getSysGroupById(id);
        if (EmptyUtils.isNotEmpty(sysGroup.getAvatar())) {
            String avatarUrl = fastDFSConfig.getDownload_url() + sysGroup.getAvatar();
            sysGroup.setAvatar(avatarUrl);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("group", sysGroup);
        return AjaxResult.success("success", result);
    }

    @PostMapping("/message/friend/agree")
    @ResponseBody
    public AjaxResult agreeAddFriend(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam("uid")String uid,
                                     @RequestParam("notifieid") String notifieid,
                                     @RequestParam("from_group") String fromGroup,
                                     @RequestParam("group") String group) throws Exception {
        SysUser fromUser = sysUserService.selectUserById(uid);
        SysUser sysUser = ShiroUtils.getSysUser();
        List<SysFriend> sysFriends = sysFriendUserService.getSysFriendByByBelong(sysUser.getUserId(),uid);
        if(EmptyUtils.isEmpty(sysFriends)) {
            //将对方添加到我的好友列表
            SysFriend sysFriend = new SysFriend();
            sysFriend.setId(StringUtils.getUUID());
            sysFriend.setBelong(sysUser.getUserId());
            sysFriend.setGroupid(group);
            sysFriend.setRemark(fromUser.getUserName());
            sysFriend.setTimestamp(System.currentTimeMillis());
            sysFriend.setUserid(uid);
            sysFriendUserService.saveSysFriend(sysFriend);
            //将我添加到对方好友列表
            SysFriend fromFriend = new SysFriend();
            fromFriend.setId(StringUtils.getUUID());
            fromFriend.setBelong(uid);
            fromFriend.setGroupid(fromGroup);
            fromFriend.setRemark(sysUser.getUserName());
            fromFriend.setTimestamp(System.currentTimeMillis());
            fromFriend.setUserid(sysUser.getUserId());
            sysFriendUserService.saveSysFriend(fromFriend);
            SysNotify sysNotify = sysNotifyService.getSysNotifyById(notifieid);
            sysNotifyService.updateSysNotifyStatus(sysNotify.getId(),2);
        }else {
            SysNotify sysNotify = sysNotifyService.getSysNotifyById(notifieid);
            sysNotifyService.updateSysNotifyStatus(sysNotify.getId(),2);
        }
        SysUser u = sysUserService.selectUserById(uid);
        if(u.getState() == 1) {
            SysMessageUser user = new SysMessageUser();
            user.setId(sysUser.getUserId());
            user.setUsername(sysUser.getUserName());
            user.setStatus(sysUser.getState()==null || sysUser.getState()==1?"online":"offline");
            user.setSign(sysUser.getSign());
            Map<String,Object> result= new HashMap<String,Object>();
            List<SysNotify> sysNotifies = sysNotifyService.findSysNotifyByHandlerId(sysUser.getUserId());
            if (EmptyUtils.isNotEmpty(sysUser.getAvatar())) {
                String avatarUrl = fastDFSConfig.getDownload_url() + sysUser.getAvatar();
                user.setAvatar(avatarUrl);
            }
            result.put("user",user);
            result.put("groupid",fromGroup);
            result.put("type",9);
            AjaxResult ajaxResult = AjaxResult.success("success",result);
            sysMessageService.sendMessage(fromUser.getUserId(), JSON.toJSONString(ajaxResult));
        }
        return AjaxResult.success("success");
    }

    @PostMapping("/message/group/agree")
    @ResponseBody
    public AjaxResult agreeAddFriendGroup(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam("notifieid") String notifieid) throws Exception {
        SysNotify sysNotify = sysNotifyService.getSysNotifyById(notifieid);
        SysUser sysUser = sysUserService.selectUserById(sysNotify.getFrom());
        SysGroupMember sysGroupMember = new SysGroupMember();
        sysGroupMember.setId(StringUtils.getUUID());
        sysGroupMember.setRemark(sysUser.getUserName());
        sysGroupMember.setGroupid(sysNotify.getGroupid());
        sysGroupMember.setStatus(0);
        sysGroupMember.setUserid(sysUser.getUserId());
        sysGroupMember.setType(0);
        sysGroupMember.setTimestamp(System.currentTimeMillis());
        sysGroupMemberService.saveSysGroupMember(sysGroupMember);
        sysNotify.setStatus(2);
        sysNotifyService.updateSysNotifyStatus(sysNotify.getId(),2);
        if(sysUser.getState() == 1) {
            Map<String,Object> result= new HashMap<String,Object>();
            result.put("notify",sysNotify);
            result.put("type",10);
            AjaxResult ajaxResult = AjaxResult.success("success",result);
            sysMessageService.sendMessage(sysUser.getUserId(), JSON.toJSONString(ajaxResult));
        }
        return AjaxResult.success("success");
    }

    /**
     * 拒绝审核
     */
    @GetMapping("/message/friend/refuse")
    @ResponseBody
    public AjaxResult refuseFriend(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("notifieid") String notifieid) {
        SysNotify sysNotify = sysNotifyService.getSysNotifyById(notifieid);
        sysNotify.setStatus(3);
        sysNotifyService.updateSysNotifyStatus(sysNotify.getId(),3);
        return AjaxResult.success("success");
    }


    /**
     * 修改个性签名
     */
    @GetMapping("/message/user/sign")
    @ResponseBody
    public AjaxResult modifyUserSign(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam("sign") String sign) {
        String userid = ShiroUtils.getUserId();
        sysUserService.updateUserSign(userid,sign);
        return AjaxResult.success("success");
    }

    /**
     * 监听用户在线状态
     */
    @GetMapping("/message/user/online")
    @ResponseBody
    public AjaxResult jugeUserOnline(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = ShiroUtils.getSysUser();
        sysUserService.updateUserState(sysUser.getUserId(),1);
        SysMessageUser sysMessageUser = new SysMessageUser();
        sysMessageUser.setId(sysUser.getUserId());
        sysMessageUser.setUsername(sysUser.getUserName());
        sysMessageUser.setSign(sysUser.getSign());
        sysMessageUser.setStatus("online");
        List<SysFriendUser> users = sysFriendUserService.findSysFriendByUserId(sysUser.getUserId());
        if (EmptyUtils.isNotEmpty(users)) {
            for (SysFriendUser friend : users) {
                Map<String,Object> result= new HashMap<String,Object>();
                result.put("user",sysMessageUser);
                result.put("type",5);
                AjaxResult ajaxResult = AjaxResult.success("success",result);
                try {
                    sysMessageService.sendMessage(friend.getId(), JSON.toJSONString(ajaxResult));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return AjaxResult.success("success");
    }


    /**
     * 用户在线状态切换
     */
    @GetMapping("/message/online/status")
    @ResponseBody
    public AjaxResult updateOnlineStatus(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam("status") String status) {
        Integer state = 0;
        int type = 5;
        if (status.equals("online")) {
            state=1;
            type=5;
        }
        if (status.equals("hide")) {
            state = 2;
            type=6;
        }
        if (status.equals("offline")) {
            state = 0;
            type=6;
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        sysUserService.updateUserState(sysUser.getUserId(),state);
        SysMessageUser sysMessageUser = new SysMessageUser();
        sysMessageUser.setId(sysUser.getUserId());
        sysMessageUser.setUsername(sysUser.getUserName());
        sysMessageUser.setSign(sysUser.getSign());
        sysMessageUser.setStatus("online");
        List<SysFriendUser> users = sysFriendUserService.findSysFriendByUserId(sysUser.getUserId());
        if (EmptyUtils.isNotEmpty(users)) {
            for (SysFriendUser friend : users) {
                Map<String,Object> result= new HashMap<String,Object>();
                result.put("user",sysMessageUser);
                result.put("type",type);
                AjaxResult ajaxResult = AjaxResult.success("success",result);
                try {
                    sysMessageService.sendMessage(friend.getId(), JSON.toJSONString(ajaxResult));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return AjaxResult.success("success");
    }

    @GetMapping("/message/validate")
    @ResponseBody
    public void validateChannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(403);
        String result = "authentication validate error!";
        String subscriberType = request.getHeader(Constants.X_SUBSCRIBER_TYPE);
        String publisherType = request.getHeader(Constants.X_PUBLISHER_TYPE);
        String channel = request.getHeader(Constants.X_CHANNEL_ID);
        String original = request.getHeader(Constants.X_ORIGINAL_URI);
        if (subscriberType != null && !"null".equals(subscriberType)) {//订阅消息
            HttpSession session = null;
            if (original.contains("?")) {//web端订阅身份验证
                String userids = original.split("\\?")[1];
                String userid = null;
                if (EmptyUtils.isNotEmpty(userids)) {
                    userid = userids.split("=")[1];
                    if (EmptyUtils.isNotEmpty(userid) && userid.contains("&")) {
                        userid=userid.split("&")[0];
                    }
                }
                result = this.validateAccountAuthentication(response,userid);
            }
        }
        if (publisherType != null && !"null".equals(publisherType)) {//发布消息 只进行内部信任IP的验证
            Boolean rs = Boolean.FALSE;
            try {
                rs = Boolean.TRUE;
                if (rs) {
                    response.setStatus(200);
                    result = "authentication validate success!";
                }
            } catch (Exception e) {
                logger.error("验证消息发布者身份出错!" + e.getMessage());
                e.printStackTrace();
            }
        }
        try (PrintWriter pw = response.getWriter()) {
            pw.println(result);
            pw.flush();
            pw.close();
        }
        response = null;
    }


    /*
     * 验证消息订阅者身份
     */
    private String validateAccountAuthentication(HttpServletResponse response, String userid) {
        if (EmptyUtils.isEmpty(userid)) {
            return "authentication validate error!";
        }
        SysUser sysUser = sysUserService.selectUserById(userid);
        if (sysUser == null) {
            return "authentication validate error!";
        }
        if (EmptyUtils.isNotEmpty(sysUser.getUserId()) && sysUser.getUserId().equals(userid)) {
            response.setStatus(200);
            return "authentication validate success!";
        }
        return "authentication validate error!";
    }
}
