import { useForm } from 'react-hook-form'
import { useNavigate } from 'react-router'
import { Search, ShieldCheck, Clock, CreditCard, Bus } from 'lucide-react'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'
import { useIsAuthenticated } from '@/store/hooks'

interface SearchForm {
  source: string
  destination: string
  seatCount: number
}

const features = [
  { icon: Bus, title: 'Wide Network', desc: 'Thousands of routes across India connecting major cities.' },
  { icon: ShieldCheck, title: 'Safe Journeys', desc: 'Verified operators with trained drivers and well-maintained buses.' },
  { icon: Clock, title: 'On-Time Guarantee', desc: 'Real-time tracking and schedule updates for every trip.' },
  { icon: CreditCard, title: 'Easy Payments', desc: 'Book instantly and get instant confirmation with refunds on cancellation.' },
]

export default function Home() {
  const navigate = useNavigate()
  const isAuthenticated = useIsAuthenticated()
  const { register, handleSubmit, formState: { errors } } = useForm<SearchForm>({
    defaultValues: { seatCount: 1 },
  })

  function onSearch(data: SearchForm) {
    navigate(`/buses/search?source=${encodeURIComponent(data.source)}&destination=${encodeURIComponent(data.destination)}&seatCount=${data.seatCount}`)
  }

  return (
    <div>
      {/* Hero */}
      <section className="bg-gradient-to-br from-primary-600 to-primary-800 text-white py-20 px-4">
        <div className="mx-auto max-w-4xl text-center">
          <h1 className="text-4xl font-bold sm:text-5xl">Book Your Bus Journey</h1>
          <p className="mt-4 text-lg text-primary-100">
            Search thousands of routes. Book instantly. Travel comfortably.
          </p>

          {/* Search card */}
          <div className="mt-10 rounded-2xl bg-white p-6 shadow-xl text-left dark:bg-gray-800">
            <form onSubmit={handleSubmit(onSearch)} className="flex flex-col sm:flex-row gap-3 items-end">
              <div className="flex-1">
                <Input
                  label="From"
                  placeholder="e.g. Mumbai"
                  error={errors.source?.message}
                  {...register('source', { required: 'Source is required' })}
                />
              </div>
              <div className="flex-1">
                <Input
                  label="To"
                  placeholder="e.g. Pune"
                  error={errors.destination?.message}
                  {...register('destination', { required: 'Destination is required' })}
                />
              </div>
              <div className="w-28">
                <Input
                  label="Seats"
                  type="number"
                  min={1}
                  max={10}
                  error={errors.seatCount?.message}
                  {...register('seatCount', { valueAsNumber: true, min: { value: 1, message: 'Min 1' } })}
                />
              </div>
              <Button type="submit" size="lg" className="gap-2 shrink-0">
                <Search className="size-4" /> Search
              </Button>
            </form>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-16 px-4 bg-white dark:bg-gray-900">
        <div className="mx-auto max-w-6xl">
          <h2 className="text-center text-2xl font-bold text-gray-900 mb-10 dark:text-gray-100">Why Book My Bus?</h2>
          <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
            {features.map(({ icon: Icon, title, desc }) => (
              <div key={title} className="rounded-xl border border-gray-100 p-6 text-center hover:shadow-md transition-shadow dark:border-gray-700 dark:hover:bg-gray-800">
                <div className="mx-auto mb-4 inline-flex size-12 items-center justify-center rounded-full bg-primary-50 dark:bg-primary-900/20">
                  <Icon className="size-6 text-primary-600" />
                </div>
                <h3 className="font-semibold text-gray-900 mb-1 dark:text-gray-100">{title}</h3>
                <p className="text-sm text-gray-500 dark:text-gray-400">{desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA */}
      <section className="bg-gray-50 py-16 px-4 dark:bg-gray-950">
        <div className="mx-auto max-w-2xl text-center">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-gray-100">Ready to travel?</h2>
          <p className="mt-3 text-gray-500 dark:text-gray-400">
            {isAuthenticated ? 'Search available routes and book your next journey.' : 'Create a free account and book your first ticket in under 2 minutes.'}
          </p>
          <div className="mt-6 flex gap-3 justify-center">
            {!isAuthenticated && (
              <Button size="lg" onClick={() => navigate('/register')}>Get Started</Button>
            )}
            <Button size="lg" variant="outline" onClick={() => navigate('/buses/search')}>
              Browse Routes
            </Button>
          </div>
        </div>
      </section>
    </div>
  )
}
