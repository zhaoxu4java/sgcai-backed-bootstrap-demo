package com.sgcai.backend.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.sgcai.backend.business.bform.RoleBForm;
import com.sgcai.backend.business.bform.RoleResourceBForm;
import com.sgcai.backend.business.bform.UserBForm;
import com.sgcai.backend.business.bto.DepartmentBTO;
import com.sgcai.backend.business.bto.ResourcesBTO;
import com.sgcai.backend.business.bto.RoleBTO;
import com.sgcai.backend.business.bto.TokenBTO;
import com.sgcai.backend.business.bto.UserRoleBTO;
import com.sgcai.backend.business.service.remote.BackendBusiness;
import com.sgcai.backend.commons.criteria.RoleCriteria;
import com.sgcai.backend.commons.criteria.UserCriteria;
import com.sgcai.backend.commons.exception.LoginException;
import com.sgcai.backend.commons.to.OnlineUserBTO;
import com.sgcai.backend.commons.type.BackendUserStatus;
import com.sgcai.backend.commons.type.ResourceType;
import com.sgcai.backend.web.constant.ConstantBackend;
import com.sgcai.backend.web.utils.CallbackType;
import com.sgcai.backend.web.utils.DWZUtils;
import com.sgcai.commons.lang.base.CollectionTO;
import com.sgcai.commons.lang.utils.Dui1DuiStringUtils;
import com.sgcai.commons.lang.utils.WebUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/backend")
public class BackendUserController {

    @Autowired
    BackendBusiness backendBusiness;

    @Value("${MANAGE_BACKEND_COOKIE_DOMAIN}")
    public String cookie_domain;

   

    @RequestMapping("user/list.do")
    @ResponseBody
    public String userList(@RequestParam int start,@RequestParam int length,
                                 HttpServletRequest request, HttpServletResponse response){
        String draw = request.getParameter("draw");
        JSONObject json = new JSONObject();
        UserCriteria userCriteria = new UserCriteria();
        String roleId = request.getParameter("roleId");
        if(StringUtils.isNotBlank(roleId)){
            userCriteria.setRoleId(roleId);
        }

        int pageNum = (start+length)/length;
        userCriteria.setPageNo(pageNum);
        userCriteria.setPageSize(length);
        CollectionTO<Map<String, Object>> usersCollection = backendBusiness.getUsersByCriteria(userCriteria);


        JSONObject returnData = new JSONObject();

        returnData.put("draw", draw);
        returnData.put("recordsTotal", usersCollection.getRecordCnt());
        returnData.put("recordsFiltered", usersCollection.getRecordCnt());
        returnData.put("data", usersCollection.getList());

        json.put("returnData",returnData);

        return json.toString();
    }

    
}
