import {FC, useState} from "react";
import styles from './sider.module.css'
import {Button, Input, Modal} from "antd";
import Image from "next/image";

interface siderProps {

}

const App:FC<siderProps> = () => {
    const [isLogined, setIsLogined] = useState(false);
    const [isChangeAvatarOpen, setIsChangeAvatarOpen] = useState(false);
    const [username, setUsername] = useState('Login');
    const [imageUrl, setImageUrl] = useState('https://cdn-icons-png.flaticon.com/512/149/149071.png')
    const [typedImageUrl, setTypedImageUrl] = useState('')

    return(
        isLogined ? <div className={styles.sider}>
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
                </div>
            </div>


            <div className={styles.lastVotes}>
                Last votes (5)
                <ul>
                    <li>Quote</li>
                    <li>Quote</li>
                    <li>Quote</li>
                </ul>
            </div>

        </div> : <div className={styles.sider}>
            <Input placeholder="login" className={styles.loginField}/>
            <Input placeholder="password" className={styles.loginField}/>
            <button className={styles.loginField} onClick={()=>setIsLogined(true)}>log in</button>
            <br/>
            <div className={styles.createAccount}>Create an account!</div>
        </div>
    )
}

export default App