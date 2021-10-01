package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;

import java.util.List;
import java.util.Optional;

public interface DialogRepository extends CrudRepository<Dialog> {
    Optional<Dialog> get(Account fromAccount, Account toAccount);

    List<DialogMessage> getMessages(Dialog dialog);
}
