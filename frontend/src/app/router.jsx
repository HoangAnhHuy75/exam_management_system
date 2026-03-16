import Home from "../pages/Home"
import DefaultLayout from "../shared/layouts/DefaultLayout"
import Exam from "../pages/Exam"
export const routes = [
    {
        path: "/",
        element: <DefaultLayout />,
        children:[
            {path:"/",element:<Home/>},
            {path:"/exam",element:<Exam/>},
        ]
    }
]