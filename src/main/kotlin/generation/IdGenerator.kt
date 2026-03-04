package lab5.generation

class IdGenerator (var nextId: Int = 1) {
    fun nextFromMax (MaxId: Int) {
        nextId = MaxId + 1
        if (nextId < 1) {
            nextId = 1
        }
    }

    fun next(): Int {
        val id = nextId
        nextId++
        return id
    }
}