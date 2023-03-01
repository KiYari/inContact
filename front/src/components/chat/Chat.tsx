import {FC, useEffect, useState} from "react";
import {Button, Input} from "antd";
import Message from "@/components/message/Message";
import message from "@/components/message/message.props";
import axios from "axios";
import {connect, disconnect, reconnect, sendMessage} from "@/util/ws";
import Cookies from "universal-cookie";

interface ChatProps{

}

const Chat:FC<ChatProps> = () => {
    const [messages, setMessages] = useState<message[]>([]);
    const [input, setInput] = useState("");
    const cookies = new Cookies
    useEffect(() => {
        connect()
        onMessageEdit()
    }, [])

    const onMessageEdit = async () => {
      await axios.get(`http://localhost:9000/message/${cookies.get('user-id')}`, {
          headers: {
              Authorization:`Bearer ${cookies.get('jwt-token')}`
          }
      }).then(res => {
          setMessages(res.data.messages)
      })
    }

    const sendMessageToServer = async () => {
        await sendMessage({userId: cookies.get('user-id'), text: input})
        await onMessageEdit()
    }

    return(
        <div>
            <Button onClick={() => connect()}>connect</Button>
            <Button onClick={() => reconnect()}>reconnect</Button>
            <Button onClick={() => disconnect()}>disconnect</Button>
            <Button onClick={() => sendMessageToServer()}>send</Button>
            <Button onClick={() => onMessageEdit()}>refresh messages</Button>

            <ul>
                <Input.Group compact>
                    <Input onChange={(e) => setInput(e.target.value)}
                           style={{ width: 'calc(25%)' }}
                           placeholder="Type message" />
                    <Button type="primary" onClick={() => sendMessageToServer()}>Save Message</Button>
                </Input.Group>
                {messages
                    //@ts-ignore
                    .sort((m1, m2) => m1.id - m2.id)
                    .map((m) => <li key={m.userId}><Message message={m}
                                                        deletedClick={onMessageEdit}
                                                        editClick={onMessageEdit}/></li>)}
            </ul>
        </div>
    )
}

export default Chat