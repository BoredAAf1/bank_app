

fun main() {
    val bank = Bank()

    bank.createAccount("Mohnish", SavingsAccount("Mohnish", 1000.0))
    bank.createAccount("Rushabh", CurrentAccount("Rushabh", 500.0))

    bank.deposit("Mohnish", 250.0)
    bank.withdraw("Rushabh", 100.0)
    bank.checkBalance("Mohnish")
    bank.checkBalance("Rushabh")
}

interface Account {
    val owner: String
    val balance: Double
    fun deposit(amount: Double)
    fun withdraw(amount: Double)
}

open class BaseAccount(override val owner: String, protected var currentBalance: Double) : Account {
    override val balance: Double
        get() = currentBalance

    override fun deposit(amount: Double) {
        currentBalance += amount
        println("Deposited Rs.$amount to $owner's account")
    }

    override fun withdraw(amount: Double) {
        if (amount > currentBalance) {
            println("Insufficient funds for $owner")
        } else {
            currentBalance -= amount
            println("Withdrew Rs.$amount from $owner's account")
        }
    }
}

class SavingsAccount(owner: String, balance: Double) : BaseAccount(owner, balance) {
    override fun withdraw(amount: Double) {
        val penalty = 10.0
        if (amount + penalty > currentBalance) {
            println("Insufficient funds for $owner after including savings withdrawal penalty")
        } else {
            currentBalance -= (amount + penalty)
            println("Withdrew Rs.$amount with Rs.$penalty penalty from $owner's savings account")
        }
    }
}

class CurrentAccount(owner: String, balance: Double) : BaseAccount(owner, balance)

class Bank {
    private val accounts = mutableMapOf<String, Account>()

    fun createAccount(name: String, account: Account) {
        if (accounts.containsKey(name)) {
            println("Account for $name already exists.")
        } else {
            accounts[name] = account
            println("Created account for $name with balance Rs.${account.balance}")
        }
    }

    fun deposit(name: String, amount: Double) {
        val account = accounts[name]
        if (account != null) {
            account.deposit(amount)
        } else {
            println("No account found for $name")
        }
    }

    fun withdraw(name: String, amount: Double) {
        val account = accounts[name]
        if (account != null) {
            account.withdraw(amount)
        } else {
            println("No account found for $name")
        }
    }

    fun checkBalance(name: String) {
        val account = accounts[name]
        if (account != null) {
            println("${account.owner}'s balance: Rs.${account.balance}")
        } else {
            println("No account found for $name")
        }
    }
}


