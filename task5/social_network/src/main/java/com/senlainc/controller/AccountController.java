package com.senlainc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.dto.AccountDto;
import com.senlainc.factory.ServiceFactory;
import com.senlainc.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/account/*")
public class AccountController extends HttpServlet {
    private AccountService accountService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        accountService = ServiceFactory.accountService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400);
        String uri = req.getRequestURI();
        Pattern pattern = Pattern.compile("^/account/(\\d+)/?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            String group = matcher.group(1);
            long id = Long.parseLong(group);
            Optional<AccountDto> accountDto = accountService.findById(id);
            if (accountDto.isPresent()) {
                resp.setStatus(200);
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), accountDto.get());
            }
            return;
        }
        pattern = Pattern.compile("^/account/?$", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            resp.setStatus(200);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), accountService.findAll());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AccountDto accountDto = objectMapper.readValue(req.getInputStream(), AccountDto.class);
            accountDto = accountService.create(accountDto);
            resp.setStatus(200);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), accountDto);
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AccountDto accountDto = objectMapper.readValue(req.getInputStream(), AccountDto.class);
            accountService.update(accountDto);
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400);
        String uri = req.getRequestURI();
        Pattern pattern = Pattern.compile("^/account/(\\d+)/?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            String group = matcher.group(1);
            long id = Long.parseLong(group);
            accountService.delete(id);
            resp.setStatus(200);
        }
    }
}
