package ru.fns.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fns.server.dto.*;
import ru.fns.server.service.WorkerService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/worker")
@Slf4j
public class WorkerController {
    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Operation(
            summary = "Получить список заявок с пагинацией",
            description = "Возвращает список заявок с возможностью пагинации"
    )
    @GetMapping("/requests")
    public ResponseEntity<List<Map<String, Object>>> getUserRequests(
            @Parameter(description = "Количество заявок на странице", required = true) @RequestParam(defaultValue = "5") int limit,
            @Parameter(description = "Номер страницы для пагинации", required = true) @RequestParam(defaultValue = "0") int offset) {
        log.info("Fetching user requests with limit={}, offset={}", limit, offset);

        List<Map<String, Object>> requests = workerService.getUserRequests(limit, offset);
        return ResponseEntity.ok(requests);
    }

    @Operation(
            summary = "Обновить статус заявки по ID",
            description = "Обновляет статус заявки по указанному ID"
    )
    @PutMapping("/request/{id}/status")
    public ResponseEntity<Boolean> updateRequestStatus(
            @Parameter(description = "Идентификатор заявки", required = true) @PathVariable UUID id,
            @Parameter(description = "Новый статус заявки", required = true) @RequestParam String newStatus) {
        log.info("Updating request status for ID={}, newStatus={}", id, newStatus);

        boolean success = workerService.updateRequestStatus(id, newStatus);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей"
    )
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        log.info("Fetching all users");

        List<Map<String, Object>> users = workerService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Обновить информацию о пользователе по ID",
            description = "Обновляет информацию о пользователе по его ID"
    )
    @PutMapping("/user/{id}")
    public ResponseEntity<Boolean> updateUser(
            @Parameter(description = "Идентификатор пользователя", required = true) @PathVariable UUID id,
            @RequestBody UserDto userUpdateDto) {
        log.info("Updating user ID={}", id);

        boolean success = workerService.updateUser(id, userUpdateDto);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Получить список всех организаций",
            description = "Возвращает список всех организаций"
    )
    @GetMapping("/organizations")
    public ResponseEntity<List<Map<String, Object>>> getAllOrganizations() {
        log.info("Fetching all organizations");

        List<Map<String, Object>> organizations = workerService.getAllOrganizations();
        return ResponseEntity.ok(organizations);
    }

    @Operation(
            summary = "Обновить организацию по ID",
            description = "Обновляет организацию по её ID"
    )
    @PutMapping("/organization/{id}")
    public ResponseEntity<Boolean> updateOrganization(
            @Parameter(description = "Идентификатор организации", required = true) @PathVariable UUID id,
            @RequestBody OrganizationDto organizationDto) {
        log.info("Updating organization ID={}", id);

        boolean success = workerService.updateOrganization(id, organizationDto);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Удалить организацию по ID",
            description = "Удаляет организацию по её ID"
    )
    @DeleteMapping("/organization/{id}")
    public ResponseEntity<Boolean> deleteOrganization(@PathVariable UUID id) {
        log.info("Deleting organization ID={}", id);

        boolean success = workerService.deleteOrganization(id);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Создать новую организацию",
            description = "Создает новую организацию"
    )
    @PostMapping("/organization")
    public ResponseEntity<Boolean> createOrganization(@RequestBody OrganizationDto organizationDto) {
        log.info("Creating new organization");

        boolean success = workerService.createOrganization(organizationDto);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Получить все налоговые начисления с пагинацией",
            description = "Возвращает список налоговых начислений с возможностью пагинации"
    )
    @GetMapping("/tax-assessments")
    public ResponseEntity<List<TaxAssessmentResponseDto>> getAllTaxAssessments(
            @Parameter(description = "Количество налоговых начислений на странице", required = true) @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Номер страницы для пагинации", required = true) @RequestParam(defaultValue = "0") int offset) {
        log.info("Fetching tax assessments with limit={}, offset={}", limit, offset);

        List<TaxAssessmentResponseDto> assessments = workerService.getAllTaxAssessments(limit, offset);
        return ResponseEntity.ok(assessments);
    }

    @Operation(
            summary = "Создать новое налоговое начисление",
            description = "Создает новое налоговое начисление"
    )
    @PostMapping("/tax-assessment")
    public ResponseEntity<Boolean> createTaxAssessment(@RequestBody TaxAssessmentRequestDto taxAssessmentDto) {
        log.info("Creating new tax assessment");

        boolean success = workerService.createTaxAssessment(taxAssessmentDto);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Обновить налоговое начисление",
            description = "Обновляет налоговое начисление по его ID"
    )
    @PutMapping("/tax-assessment/{id}")
    public ResponseEntity<Boolean> updateTaxAssessment(
            @Parameter(description = "Идентификатор налогового начисления", required = true) @PathVariable UUID id,
            @RequestBody TaxAssessmentRequestDto taxAssessmentDto) {
        log.info("Updating tax assessment ID={}", id);

        boolean success = workerService.updateTaxAssessment(id, taxAssessmentDto);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @Operation(
            summary = "Удалить налоговое начисление",
            description = "Удаляет налоговое начисление по его ID"
    )
    @DeleteMapping("/tax-assessment/{id}")
    public ResponseEntity<Boolean> deleteTaxAssessment(@PathVariable UUID id) {
        log.info("Deleting tax assessment ID={}", id);

        boolean success = workerService.deleteTaxAssessment(id);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }
}
