import { CompatClient, Stomp} from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import Cookies from "universal-cookie";
import message from "@/components/message/message.props";


var stompClient: CompatClient | null = null
const handlers: any[] = []

export const connect = () => {
    const socket = new SockJS('http://localhost:9000/ws/messaging')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, (ws: string) => {
        console.log('connected to ' + ws)
        stompClient?.subscribe('/topic/greetings', m => {
            handlers.forEach(handler => handler(JSON.parse(m.body)))
        })
    })
}

export const reconnect = () => {
    if (stompClient) {
        disconnect()
    }
    connect();
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

export function sendMessage(message: message) {
    const cookies = new Cookies
    if (stompClient !== null) {
        stompClient.send("/im/hello", {"Authorization": "Bearer " + cookies.get("jwt-token")}, JSON.stringify(message))
    }

}