package com.emprestimo.creditsystem.simulation.exception

open class ValidationException(message: String) : RuntimeException(message)

class InvalidRequestedAmountException : ValidationException("O valor solicitado deve ser maior que zero.")

class InvalidInstallmentCountException : ValidationException("O número de parcelas deve estar entre 6 e 48.")

class InvalidMonthlyIncomeException : ValidationException("A renda mensal líquida deve ser maior que zero.")

class InvalidMonthlyInstallmentException : ValidationException("A parcela gerada excede 30% da renda mensal líquida.")

