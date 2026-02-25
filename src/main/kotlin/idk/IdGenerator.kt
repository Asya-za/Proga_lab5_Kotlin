package lab5.idk

class IdGenerator (var NexId: Int = 1) {
    fun NextFromMax (MaxId: Int) {
        NexId = MaxId + 1
        if (NexId < 1) {
            NexId = 1
        }
    }

    fun Next(): Int {
        val id = NexId
        NexId++
        return id
    }
}