package com.senlainc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.factory.ServiceFactory;
import com.senlainc.service.FriendInviteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/friend/invite/*")
public class FriendInviteController extends HttpServlet {
    private FriendInviteService friendInviteService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        friendInviteService = ServiceFactory.friendInviteService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreateFriendInviteDto createFriendInviteDto = objectMapper.readValue(req.getInputStream(), CreateFriendInviteDto.class);
            FriendInviteDto friendInviteDto = friendInviteService.create(createFriendInviteDto);
            resp.setStatus(200);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), friendInviteDto);
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400);
        String uri = req.getRequestURI();
        Pattern pattern = Pattern.compile("^/friend/invite/(\\d+)/accept/?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            String group = matcher.group(1);
            long id = Long.parseLong(group);
            friendInviteService.acceptFriendInvite(id);
            resp.setStatus(200);
        }
    }
}
