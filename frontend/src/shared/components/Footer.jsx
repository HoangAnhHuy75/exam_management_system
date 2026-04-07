export default function Footer() {
  return (
    <footer className="bg-gray-900 text-gray-300 mt-20">
      
      <div className="max-w-7xl mx-auto px-6 py-14 grid md:grid-cols-4 gap-10">

        {/* Logo + description */}
        <div>
          <h2 className="text-2xl font-bold text-white mb-4">
            ExamPortal
          </h2>

          <p className="text-sm leading-relaxed text-gray-400">
            Hệ thống quản lý kỳ thi tuyển sinh giúp thí sinh đăng ký,
            theo dõi lịch thi và tra cứu kết quả nhanh chóng, chính xác
            và minh bạch.
          </p>
        </div>

        {/* Menu */}
        <div>
          <h3 className="text-white font-semibold mb-4">
            Điều hướng
          </h3>

          <ul className="space-y-2">
            <li className="hover:text-white transition cursor-pointer">
              Trang chủ
            </li>
            <li className="hover:text-white transition cursor-pointer">
              Kỳ thi
            </li>
            <li className="hover:text-white transition cursor-pointer">
              Tra cứu kết quả
            </li>
            <li className="hover:text-white transition cursor-pointer">
              Hướng dẫn đăng ký
            </li>
          </ul>
        </div>

        {/* Support */}
        <div>
          <h3 className="text-white font-semibold mb-4">
            Hỗ trợ
          </h3>

          <ul className="space-y-2">
            <li className="hover:text-white transition cursor-pointer">
              Trung tâm trợ giúp
            </li>
            <li className="hover:text-white transition cursor-pointer">
              Điều khoản sử dụng
            </li>
            <li className="hover:text-white transition cursor-pointer">
              Chính sách bảo mật
            </li>
            <li className="hover:text-white transition cursor-pointer">
              Liên hệ
            </li>
          </ul>
        </div>

        {/* Contact */}
        <div>
          <h3 className="text-white font-semibold mb-4">
            Liên hệ
          </h3>

          <ul className="space-y-2 text-sm text-gray-400">
            <li>Email: support@examportal.com</li>
            <li>Hotline: 1900 1234</li>
            <li>Địa chỉ: Hà Nội, Việt Nam</li>
          </ul>

          {/* Social */}
          <div className="flex gap-4 mt-4">
            <div className="w-9 h-9 flex items-center justify-center bg-gray-800 rounded-lg hover:bg-blue-600 transition cursor-pointer">
              f
            </div>
            <div className="w-9 h-9 flex items-center justify-center bg-gray-800 rounded-lg hover:bg-pink-600 transition cursor-pointer">
              ig
            </div>
            <div className="w-9 h-9 flex items-center justify-center bg-gray-800 rounded-lg hover:bg-sky-500 transition cursor-pointer">
              tw
            </div>
          </div>
        </div>

      </div>

      {/* bottom */}
      <div className="border-t border-gray-800 py-6 text-center text-sm text-gray-500">
        © {new Date().getFullYear()} ExamPortal. All rights reserved.
      </div>

    </footer>
  );
}