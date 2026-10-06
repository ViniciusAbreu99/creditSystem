package com.meutudo.creditsystem.proposal.exception

open class ProposalException(message: String) : RuntimeException(message)

class SimulationNotFoundException : ProposalException("A simulação informada não foi encontrada.")

class InvalidProposalException : ProposalException("Proposta inválida, a proposta recebida não pode ser aprovada/contratada ou não existe.")
