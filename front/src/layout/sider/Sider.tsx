import {FC, useEffect, useState} from "react";
import Authentication from "@/layout/sider/authentication/Authentication";
import SidePropfile from "@/layout/sider/profile/SidePropfile";
import Cookies from "universal-cookie";

interface siderProps {

}

const App:FC<siderProps> = () => {
    const cookies= new Cookies
    const [isLogined, setIsLogined] = useState(false);
    useEffect(() => {
        if(cookies.get('jwt-token')) {
            setIsLogined(true)
        } else {
            setIsLogined(false)
        }
    })


    return(
        isLogined ?
            <SidePropfile logout={() => setIsLogined(!isLogined)}/>
            : <Authentication isLogined={() => setIsLogined(!isLogined)}/>
    )
}

export default App