import { Link } from "react-router-dom";

export default function Header() {
    return (
        <header className="w-full bg-white shadow-sm">
            <div className="max-w-7xl mx-auto flex items-center justify-between px-8 py-4">

                {/* Logo */}
                <div className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                    ExamPortal
                </div>

                {/* Menu */}
                <nav className="hidden md:flex items-center gap-10 text-gray-600 font-medium">
                    <Link to={"/"}><a href="#" className="hover:text-blue-600 transition">Trang chủ</a></Link>
                    <Link to={"/exam"}><a href="#" className="hover:text-blue-600 transition">Kỳ thi</a></Link>
                    <a href="#" className="hover:text-blue-600 transition">Tra cứu kết quả</a>
                    <a href="#" className="hover:text-blue-600 transition">Hướng dẫn</a>
                    <a href="#" className="hover:text-blue-600 transition">Liên hệ</a>
                </nav>

                {/* Buttons */}
                <div className="flex items-center gap-4">
                    <button className="px-4 py-2 text-blue-600 font-medium hover:text-blue-700">
                        Đăng nhập
                    </button>

                    <button className="px-5 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition">
                        Đăng ký
                    </button>
                </div>

            </div>
        </header>
    );
}