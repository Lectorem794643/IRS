package ru.fns.server.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fns.server.service.LookupService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lookup")
@Slf4j
public class LookupController {
    private final LookupService lookupService;

    @Autowired
    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @Operation(
            summary = "Получить количество записей в таблице",
            description = "Возвращает количество записей в указанной таблице. При неправильном имени таблицы возвращает ошибку."
    )
    @GetMapping("/{tableName}/count")
    public ResponseEntity<Integer> getTableCount(
            @Parameter(description = "Имя таблицы", required = true) @PathVariable String tableName) {

        int count = lookupService.getTableCount(tableName);

        if (count == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Некорректная таблица
        }

        return ResponseEntity.ok(count);
    }

    @Operation(
            summary = "Получить все записи из таблицы",
            description = "Возвращает все записи из указанной таблицы. При неправильном имени таблицы возвращает ошибку."
    )
    @GetMapping("/{tableName}/all")
    public ResponseEntity<List<Map<String, Object>>> getAllEntities(
            @Parameter(description = "Имя таблицы", required = true) @PathVariable String tableName) {

        List<Map<String, Object>> entities = lookupService.getAllEntities(tableName);

        if (entities == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Некорректная таблица
        }

        return ResponseEntity.ok(entities);
    }
}