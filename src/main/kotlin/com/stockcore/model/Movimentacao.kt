package com.stockcore.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "movimentacao")
data class Movimentacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMovimentacao")
    val idMovimentacao: Int = 0,

    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    val produto: Produto,

    @Column(nullable = false)
    val quantidade: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoMovimentacao: TipoMovimentacao,

    @Column(name = "dataHora", nullable = false)
    var dataHora: LocalDateTime, // <--- Agora é 'var' (mutável)

    val origem: String? = null
){
    @PrePersist
    fun prePersist() {
        // Define a dataHora apenas se ela ainda não tiver sido definida
        // (ex: para casos onde você constrói a entidade e não atribui a data)
        // O Hibernate garante que este método seja chamado antes da persistência.
        if (this.dataHora == null) { // Usar 'this' é explícito, mas 'dataHora' também funciona
            this.dataHora = LocalDateTime.now()
        }
    }
}