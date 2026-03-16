import { routes } from './router';
import { useRoutes } from "react-router-dom"
import { Toaster } from "sonner";

function App() {
  const routers = useRoutes(routes)
  return (
    <div>
      <Toaster richColors position="top-right" />
      {routers}
    </div>
  );
}

export default App
