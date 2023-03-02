import styles from "@/layout/sider/sider.module.css";
import {Button, Input, Modal} from "antd";
import Image from "next/image";
import {FC, useEffect, useState} from "react";
import Cookies from "universal-cookie";
import axios from "axios";
import Router from "next/router";

interface ProfileProps {
    logout?:any
}

const SidePropfile:FC<ProfileProps> = ({logout}) => {
    const cookies = new Cookies
    const [isChangeAvatarOpen, setIsChangeAvatarOpen] = useState(false);
    const [username, setUsername] = useState('Login');
    const [imageUrl, setImageUrl] = useState('https://cdn-icons-png.flaticon.com/512/149/149071.png')
    const [typedImageUrl, setTypedImageUrl] = useState('')

    useEffect(() => {

    })

    const handleLogout = () => {
        localStorage.setItem('logined', 'false')
        cookies.remove('jwt-token')
        cookies.remove('user-id')
        Router.push("/auth/Login")
        localStorage.setItem('selectedKey', '1')
        logout()
    }

    return(<div className={styles.sider}>
        Hello, {username}
        <Modal open={isChangeAvatarOpen}
               onOk={() => setIsChangeAvatarOpen(!isChangeAvatarOpen)}
               onCancel={() => setIsChangeAvatarOpen(!isChangeAvatarOpen)}
               footer={null}>

            <Input placeholder="insert url"
                   style={{margin: '20px',
                       width: '300px'}}
                   onChange={(e) => setTypedImageUrl(e.target.value)}
                   onPressEnter={() => setImageUrl(typedImageUrl)}
            />
            <Button onClick={() => setImageUrl(typedImageUrl)}>Submit</Button>

        </Modal>

        <div className={styles.space}>
            <div className={styles.avatarHolder}>
                <Image src={imageUrl}
                       alt={'image'}
                       width={75} height={75}/>
                <div className={styles.editButton}
                     onClick={() => setIsChangeAvatarOpen(true)}>
                    edit
                </div>
                <div className={styles.logoutButton}
                        onClick={handleLogout}>
                    logout
                </div>
            </div>
        </div>
    </div>)
}

export default SidePropfile;