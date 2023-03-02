import {FC, useEffect} from "react";
import Layout from "@/layout/Layout";
import Cookies from "universal-cookie";
import Router from "next/router";
import dynamic from "next/dynamic";

interface HomeProps {

}

const Home: FC<HomeProps> = () => {
    const cookies = new Cookies
    useEffect(() => {
        if(!cookies.get('jwt-token')){
            Router.push("/auth/Login")
            localStorage.setItem('selectedKey', '1')
        }
    }, [])

    const DynamicChat = dynamic(() => import('@/components/chat/Chat'), {loading: () => <div>Loading...</div>, ssr: false})

    return(<Layout>
            <DynamicChat/>
        </Layout>
  )
}

export default Home;