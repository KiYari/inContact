import {FC, useEffect, useState} from "react";
import ky from "ky";
import { Input, Button } from "antd";
import axios from "axios";
import message from "@/components/message/message.props";
import Message from "@/components/message/Message"

interface HomeProps {

}

const Home: FC<HomeProps> = () => {
    const [messages, setMessages] = useState<message[]>([]);
    const [input, setInput] = useState("");
    useEffect(() => {
        onMessageEdit()
    }, [])

    const onMessageEdit = () => {
        axios.get("http://localhost:8080/message")
            .then(res => setMessages(res.data))
    }

    const sendMessage = async () => {
        var message: message = {
            text: input
        }

        // @ts-ignore
        var response = await axios.post("http://localhost:8080/message", message);
        onMessageEdit()
        console.log(response)
    }



    return(
      <div>
        <ul>
            <Input.Group compact>
                <Input onChange={(e) => setInput(e.target.value)}
                       style={{ width: 'calc(25%)' }}
                       placeholder="Type message" />
                <Button type="primary" onClick={sendMessage}>Save Message</Button>
            </Input.Group>
          {messages
              //@ts-ignore
              .sort((m1, m2) => m1.id - m2.id)
              .map((m) => <li key={m.id}><Message message={m}
                                                  deletedClick={onMessageEdit}
                                                  editClick={onMessageEdit}/></li>)}
        </ul>
      </div>
  )
}

export default Home;