fun getResource(name: String) = object {}.javaClass.getResourceAsStream(name)!!.bufferedReader()
