import { useState } from 'react'
import { useSearchParams, useNavigate } from 'react-router'
import { useMutation } from '@tanstack/react-query'
import { toast } from 'sonner'
import { Star } from 'lucide-react'
import { feedbackApi } from '@/api/feedback'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/Card'
import Button from '@/components/ui/Button'

function StarRating({ label, value, onChange }: { label: string; value: number; onChange: (v: number) => void }) {
  return (
    <div>
      <p className="text-sm font-medium text-gray-700 mb-1 dark:text-gray-300">{label}</p>
      <div className="flex gap-1">
        {[1, 2, 3, 4, 5].map((star) => (
          <button key={star} type="button" onClick={() => onChange(star)}>
            <Star className={`size-7 transition-colors ${star <= value ? 'fill-yellow-400 text-yellow-400' : 'text-gray-300 dark:text-gray-600'}`} />
          </button>
        ))}
      </div>
    </div>
  )
}

export default function Feedback() {
  const [params] = useSearchParams()
  const navigate = useNavigate()
  const bookingId = Number(params.get('bookingId'))

  const [overall, setOverall] = useState(0)
  const [driver, setDriver] = useState(0)
  const [service, setService] = useState(0)
  const [comment, setComment] = useState('')

  const mutation = useMutation({
    mutationFn: () =>
      feedbackApi.submit({ bookingId, overallRating: overall, driverRating: driver, serviceRating: service, comment }),
    onSuccess: () => {
      toast.success('Thank you for your feedback!')
      navigate('/bookings')
    },
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Submission failed'
      toast.error(msg)
    },
  })

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!overall || !driver || !service) { toast.error('Please fill all ratings'); return }
    mutation.mutate()
  }

  return (
    <div className="mx-auto max-w-lg px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-6 dark:text-gray-100">Leave Feedback</h1>
      <Card>
        <CardHeader><CardTitle>Rate your journey</CardTitle></CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <StarRating label="Overall Experience" value={overall} onChange={setOverall} />
            <StarRating label="Driver Rating" value={driver} onChange={setDriver} />
            <StarRating label="Service Rating" value={service} onChange={setService} />
            <div>
              <label className="text-sm font-medium text-gray-700 mb-1 block dark:text-gray-300">Comment (optional)</label>
              <textarea
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                rows={3}
                placeholder="Share your experience…"
                className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-primary-500 focus:outline-none focus:ring-2 focus:ring-primary-500/20 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-100 dark:placeholder-gray-500"
              />
            </div>
            <div className="flex gap-3">
              <Button type="button" variant="outline" onClick={() => navigate('/bookings')}>Cancel</Button>
              <Button type="submit" loading={mutation.isPending}>Submit Feedback</Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
