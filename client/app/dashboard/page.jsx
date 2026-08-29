"use client"
import React from 'react'
import {useCurrentUser} from '@/hooks/use-auth'

const DashboardPage = () => {
  const {data: user, isLoading} = useCurrentUser()

  console.log('user', user, isLoading)
  return (
    <div>DashboardPage</div>
  )
}

export default DashboardPage