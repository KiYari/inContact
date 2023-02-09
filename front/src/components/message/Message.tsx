import {FC, useState} from "react";
import {Button, Card, Input} from "antd";
import message from "@/components/message/message.props";
import axios from "axios";
interface MessageProps {
    children?: any
    message: message
    deletedClick: any
    editClick: any
}

const Message:FC<MessageProps> = ({children, message:message, deletedClick, editClick}) => {
    const [isEditable, setIsEditable] = useState(false)
    const onClickDeleteButton = () => {
        axios.delete('http://localhost:8080/message/' + message.id)
            .then(del => {
                console.log(del)
                deletedClick()
            })
    }

    const onClickEdit = () => {
        console.log('ea')
        axios.put('http://localhost:8080/message/' + message.id, message)
            .then(edited => {
                console.log(edited)
                editClick()
                setIsEditable(false)
            })

    }

    return <Card>
        {message.id}: {isEditable? <Input defaultValue={message.text}
                                          onPressEnter={() => onClickEdit()}
                                          onChange={(e) => message.text = e.target.value}/> : message.text}
        <Button style={{marginLeft: '25px'}}
                onClick={isEditable? () => onClickEdit(): () => setIsEditable(true)}
        >edit</Button>
        <Button onClick={onClickDeleteButton} style={{marginLeft: '25px'}}>x</Button>
    </Card>
}

export default Message