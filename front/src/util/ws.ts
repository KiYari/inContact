import { CompatClient, Stomp} from '@stomp/stompjs'
import SockJS from 'sockjs-client'


var stompClient: CompatClient | null = null
const handlers: any[] = []

export const connect = () => {
    const socket = new SockJS('/chat-websocket')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, (ws: string) => {
        console.log('connected to ' + ws)
        stompClient?.subscribe('/topic/activity', m => {
            handlers.forEach(handler => handler(JSON.parse(m.body)))
        })
    })
}

export function addHandler(handler: any) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}

export function sendMessage(message: any) {
    if (stompClient !== null) {
        stompClient.send("/app/changeMessage", {}, JSON.stringify(message))
    }

}