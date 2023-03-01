import {FC, ReactNode, useEffect, useState} from "react";
import Header from "@/layout/header/Header";
import Sider from "./sider/Sider";
import styles from "./layout.module.css";
import {useRouter} from "next/router";

interface layoutProps {
    children: ReactNode
}

const Layout: FC<layoutProps> = ({children}) => {
    const router = useRouter();
    const [isLoginPage, setIsLoginPage] = useState(false);
    useEffect(() => {
        setIsLoginPage(router.pathname==='/auth/Login')
    })

    return(
        <div>
            {isLoginPage? <><Header/>
                <div className={styles.contentHolder}>
                    <main className={styles.mainCut}>
                        <Sider/>
                    </main>
                </div>
            </>
                :
                <><Header/>
                <div className={styles.contentHolder}>
                    <main className={styles.main}>
                        {children}
                    </main>

                    <Sider/>
                </div>
            </>}
        </div>
    )
}

export default Layout;