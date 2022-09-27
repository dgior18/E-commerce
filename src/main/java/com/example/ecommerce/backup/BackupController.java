package com.example.ecommerce.backup;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v*/backup")
@AllArgsConstructor
public class BackupController {
    private final BackupService backupService;

    @PostMapping
    public String backup(@RequestParam("idNumber") Long idNumber){
        return backupService.backup(idNumber);
    }

    @PatchMapping(path = "reset")
    public String reset(@RequestParam("idNumber") Long idNumber, @RequestParam("newPass") String newPass) {
        return backupService.reset(idNumber, newPass);
    }
}
