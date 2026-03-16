import Home from "../pages/Home"
import DefaultLayout from "../shared/layouts/DefaultLayout"
import Major from "../pages/Major"
export const routes = [
    {
        path: "/",
        element: <DefaultLayout />,
        children:[
            {path:"/" , element:<Home/>},
            {path:"/major" , element:<Major/>},
        ]
    }
]