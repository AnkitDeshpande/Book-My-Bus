import { cn } from '@/lib/utils'

export default function Spinner({ className }: { className?: string }) {
  return (
    <div className={cn('size-8 animate-spin rounded-full border-4 border-gray-200 border-t-primary-600', className)} />
  )
}

export function PageSpinner() {
  return (
    <div className="flex min-h-[60vh] items-center justify-center">
      <Spinner className="size-10" />
    </div>
  )
}
