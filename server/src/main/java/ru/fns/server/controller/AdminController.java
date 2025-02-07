package ru.fns.server.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fns.server.dto.UserDto;
import ru.fns.server.service.AdminService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(
            summary = "Получить пользователей",
            description = "Получить список пользователей с возможностью фильтрации по имени и поддержкой пагинации"
    )
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getUsersForName(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String name) {

        log.info("Fetching users with limit={}, offset={}, name={}", limit, offset, name);
        List<Map<String, Object>> users = adminService.getUsersForName(limit, offset, name != null ? name : "");

        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "Создать нового пользователя, предоставив его данные"
    )
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto);
        boolean isCreated = adminService.createUser(
                userDto.getName(), userDto.getAuthority(), userDto.getLogin(),
                userDto.getEmail(), userDto.getPhone());

        return isCreated
                ? ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка создания пользователя");
    }

    @Operation(
            summary = "Обновить пользователя",
            description = "Обновить данные существующего пользователя по его ID"
    )
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        log.info("Updating user with id={}: {}", id, userDto);
        boolean isUpdated = adminService.updateUser(
                id, userDto.getName(), userDto.getAuthority(),
                userDto.getLogin(), userDto.getEmail(), userDto.getPhone());

        return isUpdated
                ? ResponseEntity.ok("Пользователь успешно обновлен")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка обновления пользователя");
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Удалить существующего пользователя по его ID"
    )
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        log.info("Deleting user with id={}", id);
        boolean isDeleted = adminService.deleteUser(id);

        return isDeleted
                ? ResponseEntity.ok("Пользователь успешно удален")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
    }

    @Operation(
            summary = "Сбросить пароль пользователя",
            description = "Сбросить пароль пользователя на стандартный ('qwerty123') по его ID"
    )
    @PutMapping("/users/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable UUID id) {
        log.info("Resetting password for user with id={}", id);
        boolean isReset = adminService.resetPassword(id);

        return isReset
                ? ResponseEntity.ok("Пароль успешно сброшен")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка сброса пароля");
    }
}
