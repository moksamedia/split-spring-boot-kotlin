package com.example.demo

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*
import io.split.client.SplitClient

@RestController
class PaperAirplaneController(val repository: PaperAirplaneRepository, val splitClient: SplitClient) {

    @GetMapping("principal")
    fun info(principal: Principal): String {
        return principal.toString();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    fun index(principal: Principal, @RequestParam testAttribute: String): List<PaperAirplane>? {
        // Create the attribute map
        val attributes: Map<String, String> = mapOf("testAttribute" to testAttribute)
        // Pass the attribute to the getTreament() method
        val treatment: String = splitClient.getTreatment(principal.name, "moreTesting", attributes)
        println("username=${principal.name}, testAttribute=${testAttribute}, treatment=$treatment")
        return if (treatment == "every") {
            repository.findAll().toList()
        }
        else if (treatment == "non-test") {
            repository.findByIsTesting(false)
        }
        else if (treatment == "test-only") {
            repository.findByIsTesting(true)
        }
        else { // none
            return emptyList();
        }
    }



    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun post(@RequestParam name: String, @RequestParam folds: Int, @RequestParam isTesting: Boolean, principal: Principal): PaperAirplane {
        val paperAirplane = PaperAirplane(null, name, folds, isTesting);
        repository.save(paperAirplane);
        return paperAirplane;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping
    fun post(@RequestParam name: String, @RequestParam folds: Int, @RequestParam id: Long, @RequestParam isTesting: Boolean, principal: Principal): ResponseEntity<PaperAirplane> {
        val paperAirplane: Optional<PaperAirplane> = repository.findById(id)
        return if (paperAirplane.isPresent) {
            paperAirplane.get().name = name
            paperAirplane.get().folds = folds
            paperAirplane.get().isTesting = isTesting
            repository.save(paperAirplane.get())
            ResponseEntity.ok(paperAirplane.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping
    fun delete(@RequestParam id: Long, principal: Principal): ResponseEntity<String> {
        return if (repository.existsById(id)) {
            val response = repository.deleteById(id);
            print(response);
            ResponseEntity.ok("Deleted");
        } else {
            ResponseEntity.notFound().build();
        }
    }
}
