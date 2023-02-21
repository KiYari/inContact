import {FC, ReactNode} from "react";
import Header from "@/layout/header/Header";
import Sider from "./sider/Sider";
import styles from "./layout.module.css";
interface layoutProps {
    children: ReactNode
}

const Layout: FC<layoutProps> = ({children}) => {
    return(
        <div>
            <Header/>

            <div className={styles.contentHolder}>
                <main className={styles.main}>
                    {children}
                </main>

                <Sider/>
            </div>



        </div>
    )
}

export default Layout;