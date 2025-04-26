package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    /**
     * Lấy danh sách tất cả Account
     */
    List<Account> getAllAccounts();

    /**
     * Tìm Account theo username
     */
    Optional<Account> getByUsername(String username);

    /**
     * Tạo mới Account.
     * - Nếu username đã tồn tại, có thể ném exception hoặc trả về Optional.empty()
     * - Mã hóa password trước khi lưu
     */
    Account createAccount(Account account);

    /**
     * Cập nhật Account.
     * - Nếu có thay đổi password, mã hóa lại
     */
    Account updateAccount(Account account);

    /**
     * Xóa Account theo username
     */
    void deleteAccount(String username);
}

