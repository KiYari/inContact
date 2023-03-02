import {FC, useEffect, useState} from "react";
import styles from "@/layout/sider/sider.module.css";
import {Input} from "antd";
import Cookies from "universal-cookie";
import axios, {AxiosError, AxiosResponse} from "axios";
import Router from "next/router";
import {host} from "@/util/host.config";

interface AuthProps {
    isLogined?: any
    incorrect?: any
    className?: any
}

interface Auth{
    name: string
    password: string
}

const Authentication:FC<AuthProps> = ({isLogined, incorrect, className}) => {
    const [login, setLogin] = useState('')
    const [errorText, setErrorText] = useState('')
    const [password, setPassword] = useState('')
    const [invalid, setInvalid] = useState(false)
    const cookies = new Cookies

    const getToken = async ():Promise<AxiosResponse> => {
        var auth: Auth = {
            name: login,
            password: password
        }

        return await axios.post(`http://${host}:8000/api/auth/login`, auth,
            {
                headers: {
                    "Content-type": "application/json",
                    'Access-Control-Allow-Origin': '*',
                },
            })
    }

    const handleLogin = () => {
        getToken()
            .then(res => {
                cookies.set('jwt-token', res.data.token)
                cookies.set('user-id', res.data.id)
                localStorage.setItem('logined', 'true')
                Router.push("/")
                localStorage.setItem('selectedKey', '0')
                isLogined()
            })
            .catch((e:AxiosError) => {
            if (e.response) {
                //@ts-ignore
                setErrorText(e.response.data.message)
                console.log(e.response)
            }
            else console.log(e)
            })
        setInvalid(true)
    }

    return(<div className={`${styles.sider} ${className}`}>
        <Input placeholder="login"
               onChange={(e) => {setLogin(e.target.value)}}
               className={styles.loginField}/>
    <Input placeholder="password"
           onChange={(e) => {setPassword(e.target.value)}}
           className={styles.loginField}/>
        {invalid && <div className={styles.invalid}>
            {errorText}<br/>
        </div>}


    <button className={styles.loginField} onClick={() => handleLogin()}>log in</button>
    <br/>
    <div className={styles.createAccount}>Create an account!</div>
    </div>)
}

export default Authentication