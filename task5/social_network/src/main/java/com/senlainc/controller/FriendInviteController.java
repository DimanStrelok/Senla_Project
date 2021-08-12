package com.senlainc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.FriendInvite;
import com.senlainc.entity.Relation;
import com.senlainc.repository.FriendInviteRepository;
import com.senlainc.repository.RelationRepository;
import com.senlainc.repository.Repository;
import com.senlainc.service.FriendInviteService;
import com.senlainc.service.FriendInviteServiceImpl;
import com.senlainc.service.RelationService;
import com.senlainc.service.RelationServiceImpl;

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
        Repository<FriendInvite> friendInviteRepository = new FriendInviteRepository();
        Repository<Relation> relationRepository = new RelationRepository();
        RelationService relationService = new RelationServiceImpl(relationRepository);
        friendInviteService = new FriendInviteServiceImpl(friendInviteRepository, relationService);
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
        Pattern pattern = Pattern.compile("/friend/invite/(\\d+)/accept", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            String group = matcher.group(1);
            long id = Long.parseLong(group);
            friendInviteService.acceptFriendInvite(id);
            resp.setStatus(200);
        }
    }
}
