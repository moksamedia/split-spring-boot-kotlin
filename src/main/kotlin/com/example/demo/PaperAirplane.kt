package com.example.demo

import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// Defines the data model
@Entity
data class PaperAirplane(
    @GeneratedValue @Id var id: Long? = null,
    var name: String,
    var folds: Int,
    var isTesting: Boolean = true
)

// Enables persistence of PaperAirplane entity data model
interface PaperAirplaneRepository : CrudRepository<PaperAirplane, Long> {
    fun findByName(name: String): PaperAirplane?
    fun findByIsTesting(value: Boolean): List<PaperAirplane>?
}
