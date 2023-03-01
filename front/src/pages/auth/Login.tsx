import {FC} from "react";
import Layout from "@/layout/Layout";
import Cookies from "universal-cookie";
import Router from "next/router";

interface LoginProps {

}

const Login:FC<LoginProps> = () => {
    const cookies = new Cookies

    if (cookies.get('jwt-token')) {
        Router.push("/")
        localStorage.setItem('selectedKey', String(0))
    }

    return(<Layout>
        </Layout>
    )
}

export default Login