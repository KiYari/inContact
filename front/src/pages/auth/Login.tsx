import {FC, useEffect} from "react";
import Layout from "@/layout/Layout";
import Cookies from "universal-cookie";
import Router from "next/router";

interface LoginProps {

}

const Login:FC<LoginProps> = () => {
    const cookies = new Cookies

    useEffect(() => {
        if(cookies.get('jwt-token')){
            localStorage.setItem('selectedKey', '0')
            Router.push("/")

        }
    }, [])
    return(<Layout>
            <div/>
        </Layout>
    )
}

export default Login