import { Link } from 'react-router'
import { Bus, Mail, Phone } from 'lucide-react'

export default function Footer() {
  return (
    <footer className="border-t border-gray-200 bg-gray-50 mt-auto dark:bg-gray-900 dark:border-gray-700">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 py-10">
        <div className="grid grid-cols-1 gap-8 sm:grid-cols-3">
          <div>
            <div className="flex items-center gap-2 text-primary-600 font-bold text-lg mb-3">
              <Bus className="size-5" />
              <span>Book My Bus</span>
            </div>
            <p className="text-sm text-gray-500 dark:text-gray-400">Fast, easy, and affordable bus ticket booking across India.</p>
          </div>
          <div>
            <h4 className="text-sm font-semibold text-gray-900 mb-3 dark:text-gray-100">Quick Links</h4>
            <ul className="space-y-2 text-sm text-gray-500 dark:text-gray-400">
              <li><Link to="/buses/search" className="hover:text-primary-600 dark:hover:text-primary-400">Search Buses</Link></li>
              <li><Link to="/login" className="hover:text-primary-600 dark:hover:text-primary-400">Login</Link></li>
              <li><Link to="/register" className="hover:text-primary-600 dark:hover:text-primary-400">Register</Link></li>
            </ul>
          </div>
          <div>
            <h4 className="text-sm font-semibold text-gray-900 mb-3 dark:text-gray-100">Contact</h4>
            <ul className="space-y-2 text-sm text-gray-500 dark:text-gray-400">
              <li className="flex items-center gap-2"><Mail className="size-4" /> support@bookmybus.com</li>
              <li className="flex items-center gap-2"><Phone className="size-4" /> +91 98765 43210</li>
            </ul>
          </div>
        </div>
        <div className="mt-8 border-t border-gray-200 pt-6 text-center text-xs text-gray-400 dark:border-gray-700 dark:text-gray-500">
          © {new Date().getFullYear()} Book My Bus. All rights reserved.
        </div>
      </div>
    </footer>
  )
}
