fun main(args: Array<String>) {
    Javalin.create().apply {
        ws("/chat") { ws ->
            ws.onConnect { session ->
                val username = "User" + nextUserNumber++
                userUsernameMap.put(session, username)
                broadcastMessage("Server", username + " joined the chat")
            }
            ws.onClose { session, status, message ->
                val username = userUsernameMap[session]
                userUsernameMap.remove(session)
                broadcastMessage("Server", username + " left the chat")
            }
            ws.onMessage { session, message ->
                broadcastMessage(userUsernameMap[session]!!, message)
            }
        }

    }
}
