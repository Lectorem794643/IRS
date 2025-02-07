package ru.fns.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fns.server.dto.CreateRequestDto;
import ru.fns.server.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Получить налоговые начисления пользователя",
            description = "Возвращает список налоговых начислений для указанного пользователя по его идентификатору"
    )
    @GetMapping("/{uid}/tax-assessments")
    public ResponseEntity<List<Map<String, Object>>> getTaxAssessments(
            @Parameter(description = "Идентификатор пользователя", required = true) @PathVariable UUID uid) {
        log.info("Fetching tax assessments for user: {}", uid);

        List<Map<String, Object>> taxAssessments = userService.getTaxAssessmentsByUid(uid);
        return ResponseEntity.ok(taxAssessments);
    }

    @Operation(
            summary = "Получить список заявок пользователя",
            description = "Возвращает список заявок для указанного пользователя по его идентификатору"
    )
    @GetMapping("/{uid}/requests")
    public ResponseEntity<List<Map<String, Object>>> getRequestsByUser(
            @Parameter(description = "Идентификатор пользователя", required = true) @PathVariable UUID uid) {
        log.info("Fetching requests for user: {}", uid);

        List<Map<String, Object>> requests = userService.getRequestsByUserId(uid);
        return ResponseEntity.ok(requests);
    }

    @Operation(
            summary = "Создать новую заявку от пользователя",
            description = "Создает новую заявку от указанного пользователя. Возвращает статус успешности операции."
    )
    @PostMapping("/{uid}/requests")
    public ResponseEntity<Boolean> createRequest(
            @Parameter(description = "Идентификатор пользователя", required = true) @PathVariable UUID uid,
            @RequestBody CreateRequestDto requestDto) {
        log.info("Creating request for user: {}, theme: {}", uid, requestDto.getTheme());

        boolean isCreated = userService.createRequest(uid, requestDto.getTheme(), requestDto.getBody());

        return isCreated
                ? ResponseEntity.status(HttpStatus.CREATED).body(true)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
}


